package org.jboss.xavier.integrations.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.tarfile.TarSplitter;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.UserService;
import org.jboss.xavier.integrations.route.dataformat.CustomizedMultipartDataFormat;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.apache.camel.builder.PredicateBuilder.not;

/**
 * A Camel Java8 DSL Router
 */
@Component
public class MainRouteBuilder extends RouteBuilder {

    public static final String UPLOADFORMDATA = "uploadformdata";
    public static final String MA_METADATA = "MA_metadata";
    public static String ANALYSIS_ID = "analysisId";
    public static String USERNAME = "analysisUsername";

    public static final String CORRELATION_ID = "correlationId";
    public static final String WORKING_DIR = "workingFile";
    public static final String WORKING_FILE = "workingFile";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";

    @Value("${insights.upload.host}")
    private String uploadHost;

    @Value("${insights.kafka.host}")
    private String kafkaHost;

    @Value("${insights.upload.mimetype}")
    private String mimeType;

    @Value("${insights.upload.accountnumber}")
    private String accountNumber;

    @Value("#{'${insights.properties}'.split(',')}")
    protected List<String> insightsProperties;

    @Value("${camel.springboot.tracing}")
    private boolean tracingEnabled;

    @Inject
    private AnalysisService analysisService;

    @Inject
    private UserService userService;

    private List<Integer> httpSuccessCodes = Arrays.asList(HttpStatus.SC_OK, HttpStatus.SC_CREATED, HttpStatus.SC_ACCEPTED, HttpStatus.SC_NO_CONTENT);

    public void configure() {
        getContext().setTracing(tracingEnabled);

        from("rest:post:/upload?consumes=multipart/form-data")
                .id("rest-upload")
                .to("direct:check-authenticated-request")
                .to("direct:upload");

        from("direct:upload").id("direct-upload")
                .unmarshal(new CustomizedMultipartDataFormat())
                .choice()
                    .when(isAllExpectedParamsExist())
                        .split()
                            .attachments()
                            .process(processMultipart())
                            .filter(isFilePart())
                                .to("direct:store")
                            .end()
                        .end()
                    .endChoice()
                    .otherwise()
                      .process(httpError400())
                    .end();

        from("direct:analysis-model").id("analysis-model-creation")
                .process(e -> {
                    String userName = e.getIn().getHeader(USERNAME, String.class);
                    AnalysisModel analysisModel = analysisService.buildAndSave((String) e.getIn().getHeader(MA_METADATA, Map.class).get("reportName"),
                            (String) e.getIn().getHeader(MA_METADATA, Map.class).get("reportDescription"),
                            (String) e.getIn().getHeader(Exchange.FILE_NAME), userName);
                    e.getIn().getHeader(MA_METADATA, Map.class).put(ANALYSIS_ID, analysisModel.getId().toString());
                });

        from("direct:store").id("direct-store")
                .convertBodyTo(byte[].class) // we need this to fully read the stream and close it
                .to("direct:analysis-model")
                .to("direct:insights");

        from("direct:insights").id("call-insights-upload-service")
                .doTry()
                    .process(this::createMultipartToSendToInsights)
                    .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
                    .setHeader("x-rh-identity", method(MainRouteBuilder.class, "getRHIdentity(${header.x-rh-identity}, ${header.CamelFileName}, ${headers})"))
                    .removeHeaders("Camel*")
                    .to("http4://" + uploadHost + "/api/ingress/v1/upload")
                    .choice()
                        .when(not(isResponseSuccess()))
                            .throwException(org.apache.commons.httpclient.HttpException.class, "Unsuccessful response from Insights Upload Service")
                .endDoTry()
                .doCatch(Exception.class)
                    .to("log:error?showCaughtException=true&showStackTrace=true")
                    .to("direct:mark-analysis-fail")
                .end();

        from("direct:mark-analysis-fail").id("markAnalysisFail")
                .process(e -> analysisService.updateStatus(AnalysisService.STATUS.FAILED.toString(), Long.parseLong((String) e.getIn().getHeader(MA_METADATA, Map.class).get(ANALYSIS_ID))))
                .stop();


        from("kafka:" + kafkaHost + "?topic={{insights.kafka.upload.topic}}&brokers=" + kafkaHost + "&autoOffsetReset=latest&autoCommitEnable=true")
                .id("kafka-upload-message")
                .unmarshal().json(JsonLibrary.Jackson, FilePersistedNotification.class)
                .filter(simple("'{{insights.service}}' == ${body.getService}"))
                .to("direct:download-file");

        from("direct:download-file")
                .id("download-file")
                .setHeader("Exchange.HTTP_URI", simple("${body.url}"))
                .convertBodyTo(FilePersistedNotification.class)
                .setHeader(MA_METADATA, method(MainRouteBuilder.class, "extractMAmetadataHeaderFromIdentity(${body})"))
                .setHeader(USERNAME, method(MainRouteBuilder.class, "getUserNameFromRHIdentity(${body.b64_identity})"))
                .setBody(constant(""))
                .doTry()
                    .to("http4://oldhost")
                    .choice()
                        .when(isResponseSuccess())
                            .removeHeader("Exchange.HTTP_URI")
                            .to("direct:unzip-file")
                            .log("File ${header.CamelFileName} success")
                        .otherwise()
                            .throwException(org.apache.commons.httpclient.HttpException.class, "Unsuccessful response from Insights Download Service")
                .endDoTry()
                .doCatch(Exception.class)
                    .to("log:error?showCaughtException=true&showStackTrace=true")
                    .to("direct:mark-analysis-fail")
                .end();

        from("direct:unzip-file")
                .id("unzip-file")
                .choice()
                    .when(isZippedFile("zip"))
                        .split(new ZipSplitter()).aggregationStrategy(this::calculateICSAggregated)
                        .streaming()
                        .to("direct:calculate")
                    .endChoice()
                    .when(isZippedFile("tar.gz"))
                        .unmarshal().gzip()
                        .split(new TarSplitter()).aggregationStrategy(this::calculateICSAggregated)
                            .streaming()
                            .to("direct:calculate")
                        .end()
                    .endChoice()
                    .otherwise()
                        .to("direct:calculate")
                        .process(exchange -> exchange.getIn().getHeaders().put(UPLOADFORMDATA, exchange.getIn().getBody(UploadFormInputDataModel.class)))
                    .end()
                .end()
                // at this time it's safe to generate the WSR
                .to("direct:send-costsavings")
                .to("direct:calculate-workloadsummaryreportmodel");

        from("direct:calculate")
                .id("calculate")
                .convertBodyTo(String.class)
                .multicast().aggregationStrategy(new GroupedBodyAggregationStrategy())
                    .to("direct:calculate-costsavings", "direct:calculate-vmworkloadinventory", "direct:flags-shared-disks")
                .setBody(e -> e.getIn().getBody(List.class).get(0))
                .end();


        from("direct:send-costsavings").id("send-costsavings")
                .setBody(header(UPLOADFORMDATA))
                .log("Message to send to AMQ : ${body}")
                .to("jms:queue:uploadFormInputDataModel")
                .end();

        from("direct:calculate-costsavings").id("calculate-costsavings")
                .transform().method("calculator", "calculate(${body}, ${header.${type:org.jboss.xavier.integrations.route.MainRouteBuilder.MA_METADATA}})")
                .end();

        from("direct:check-authenticated-request")
                .id("check-authenticated-request")
                .to("direct:add-username-header")
                .choice()
                    .when(header(USERNAME).isEqualTo(""))
                    .to("direct:request-forbidden");

        from("direct:check-authorized-request")
                .id("check-authorized-request")
                .choice()
                    .when(exchange -> {
                        String username = (String) exchange.getIn().getHeader(USERNAME);
                        return !userService.isUserAllowedToAdministratorResources(username);
                    })
                    .to("direct:request-forbidden");

        from("direct:add-username-header")
                .id("add-username-header")
                .process(exchange ->  {
                    String userName = this.getUserNameFromRHIdentity(exchange.getIn().getHeader("x-rh-identity", String.class));
                    exchange.getIn().setHeader(USERNAME, userName);
                });

        from("direct:request-forbidden")
                .id("request-forbidden")
                .process(httpError403());
    }

    private Exchange calculateICSAggregated(Exchange old, Exchange neu) {
            UploadFormInputDataModel newObj = (UploadFormInputDataModel) neu.getIn().getBody(List.class).get(0);
            UploadFormInputDataModel oldObj = (old != null) ? old.getIn().getHeader(UPLOADFORMDATA, UploadFormInputDataModel.class) : null;
            newObj.setTotalDiskSpace(newObj.getTotalDiskSpace() + ((oldObj != null) ? oldObj.getTotalDiskSpace() : 0));
            newObj.setHypervisor(newObj.getHypervisor() + ((oldObj != null) ? oldObj.getHypervisor() : 0));
            neu.getIn().getHeaders().put(UPLOADFORMDATA, newObj);
        return neu;
    }

    private Predicate isResponseSuccess() {
        return e -> httpSuccessCodes.contains(e.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class));
    }

    public Map<String,String> extractMAmetadataHeaderFromIdentity(FilePersistedNotification filePersistedNotification) throws IOException {
        String identity_json = new String(Base64.getDecoder().decode(filePersistedNotification.getB64_identity()));
        JsonNode node= new ObjectMapper().reader().readTree(identity_json);

        Map header = new HashMap<String, String>();
        JsonNode internalNode = node.get("identity").get("internal");
        internalNode.fieldNames().forEachRemaining(field -> header.put(field, internalNode.get(field).asText()));
        return header;
    }

    private Processor httpError400() {
        return exchange -> {
          exchange.getIn().setBody("{ \"error\": \"Bad Request\"}");
          exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
          exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
        };
    }

    private Processor httpError403() {
        return exchange -> {
          exchange.getIn().setBody("Forbidden");
          exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpServletResponse.SC_FORBIDDEN);
          exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        };
    }

    private Predicate isAllExpectedParamsExist() {
        return exchange -> insightsProperties.stream().allMatch(e -> StringUtils.isNoneEmpty((String)(exchange.getIn().getHeader(MA_METADATA, new HashMap<String,Object>(), Map.class)).get(e)));
    }

    private Predicate isFilePart() {
        return exchange -> exchange.getIn().getHeader(CustomizedMultipartDataFormat.CONTENT_DISPOSITION, String.class).contains("filename");
    }

    private void createMultipartToSendToInsights(Exchange exchange) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);

        byte[] file = exchange.getIn().getBody(byte[].class);
        multipartEntityBuilder.addPart("file", new ByteArrayBody(file, ContentType.create(mimeType), exchange.getIn().getHeader(Exchange.FILE_NAME, String.class)));
        exchange.getIn().setBody(multipartEntityBuilder.build());
    }

    public String getRHIdentity(String x_rh_identity_base64, String filename, Map<String, Object> headers) throws IOException {
        JsonNode node= new ObjectMapper().reader().readTree(new String(Base64.getDecoder().decode(x_rh_identity_base64)));

        ObjectNode internalNode = (ObjectNode) node.get("identity").get("internal");
        internalNode.put("filename", filename);

        // we add all properties defined on the Insights Properties, that we should have as Headers of the message
        Map<String, String> ma_metadataCasted = (Map<String, String>) headers.get(MA_METADATA);
        insightsProperties.forEach(e -> internalNode.put(e, ma_metadataCasted.get(e)));
        // add the 'analysis_id' value
        String analysisId = ma_metadataCasted.get(ANALYSIS_ID);
        if (analysisId == null)
        {
            throw new IllegalArgumentException("'" + ANALYSIS_ID + "' field not available but it's mandatory");
        }
        internalNode.put(ANALYSIS_ID, analysisId);

        return Base64.getEncoder().encodeToString(node.toString().getBytes(StandardCharsets.UTF_8));
    }

    public String getUserNameFromRHIdentity(String x_rh_identity_base64) {
        String result = "";
        try {
            JsonNode node = new ObjectMapper().reader().readTree(new String(Base64.getDecoder().decode(x_rh_identity_base64)));
            JsonNode usernameNode = node.get("identity").get("user").get("username");
            result = usernameNode.textValue();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).warning("Unable to retrieve the 'username' field from cookies due to the following exception. Hence 'username' value set to '" + result + "'.");
            e.printStackTrace();
        }
        return result;
    }

    private Predicate isZippedFile(String extension) {
        return exchange -> {
            String filename = (String) exchange.getIn().getHeader(MA_METADATA, Map.class).get("filename");
            return extension.equalsIgnoreCase(filename.substring(filename.length() - extension.length()));
        };
    }

    private Processor processMultipart() {
        return exchange -> {
            Attachment body = exchange.getIn().getBody(Attachment.class);

            DataHandler dataHandler = body.getDataHandler();

            exchange.getIn().setHeader(Exchange.FILE_NAME, dataHandler.getName());
            exchange.getIn().setHeader(CustomizedMultipartDataFormat.CONTENT_TYPE, dataHandler.getContentType());
            exchange.getIn().setHeader(CustomizedMultipartDataFormat.CONTENT_DISPOSITION, body.getHeader(CustomizedMultipartDataFormat.CONTENT_DISPOSITION));
            exchange.getIn().setBody(dataHandler.getInputStream());
        };
    }

}
