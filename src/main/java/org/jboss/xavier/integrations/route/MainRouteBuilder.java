package org.jboss.xavier.integrations.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Attachment;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.tarfile.TarSplitter;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.route.dataformat.CustomizedMultipartDataFormat;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.camel.builder.PredicateBuilder.not;

/**
 * A Camel Java8 DSL Router
 */
@Component
public class MainRouteBuilder extends RouteBuilder {

    public static String ANALYSIS_ID = "analysisId";

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

    @Inject
    private AnalysisService analysisService;

    private List<Integer> httpSuccessCodes = Arrays.asList(HttpStatus.SC_OK, HttpStatus.SC_CREATED, HttpStatus.SC_ACCEPTED, HttpStatus.SC_NO_CONTENT);

    public void configure() {

        from("rest:post:/upload?consumes=multipart/form-data")
                .id("rest-upload")
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
                    AnalysisModel analysisModel = analysisService.buildAndSave((String) e.getIn().getHeader("MA_metadata", Map.class).get("reportName"),
                            (String) e.getIn().getHeader("MA_metadata", Map.class).get("reportDescription"),
                            (String) e.getIn().getHeader("CamelFileName"));
                    e.getIn().getHeader("MA_metadata", Map.class).put(ANALYSIS_ID, analysisModel.getId().toString());
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
                .process(e -> analysisService.updateStatus(AnalysisService.STATUS.FAILED.toString(), Long.parseLong((String) e.getIn().getHeader("MA_metadata", Map.class).get(ANALYSIS_ID))))
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
                .setHeader("MA_metadata", method(MainRouteBuilder.class, "extractMAmetadataHeaderFromIdentity(${body})"))
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
                        .split(new ZipSplitter())
                        .streaming()
                        .to("direct:calculate")
                    .endChoice()
                    .when(isZippedFile("tar.gz"))
                        .unmarshal().gzip()
                        .split(new TarSplitter())
                        .streaming()
                        .to("direct:calculate")
                    .endChoice()
                    .otherwise()
                        .to("direct:calculate")
                .end();

        from("direct:calculate")
                .id("calculate")
                .convertBodyTo(String.class)
                .multicast()
                    .to("direct:calculate-costsavings", "direct:calculate-vmworkloadinventory")
                .end();


        from("direct:calculate-costsavings")
                .id("calculate-costsavings")
                .transform().method("calculator", "calculate(${body}, ${header.MA_metadata})")
                .log("Message to send to AMQ : ${body}")
                .to("jms:queue:uploadFormInputDataModel")
                .end();
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

    private Predicate isAllExpectedParamsExist() {
        return exchange -> insightsProperties.stream().allMatch(e -> StringUtils.isNoneEmpty((String)(exchange.getIn().getHeader("MA_metadata", new HashMap<String,Object>(), Map.class)).get(e)));
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
        Map<String, String> ma_metadataCasted = (Map<String, String>) headers.get("MA_metadata");
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

    private Predicate isZippedFile(String extension) {
        return exchange -> {
            String filename = (String) exchange.getIn().getHeader("MA_metadata", Map.class).get("filename");
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
