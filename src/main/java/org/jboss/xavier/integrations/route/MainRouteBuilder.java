package org.jboss.xavier.integrations.route;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Attachment;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.tarfile.TarSplitter;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.jboss.xavier.integrations.route.dataformat.CustomizedMultipartDataFormat;
import org.jboss.xavier.integrations.route.model.RHIdentity;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Camel Java8 DSL Router
 */
@Component
public class MainRouteBuilder extends RouteBuilder {

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

    public void configure() {
        getContext().setTracing(true);

        from("rest:post:/upload?consumes=multipart/form-data")
                .id("rest-upload")
                .to("direct:upload");

        from("direct:upload")
                .id("direct-upload")
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


        from("direct:store")
                .id("direct-store")
                .convertBodyTo(String.class)
                .to("file:./upload")
                .to("direct:insights");

        from("direct:insights")
                .id("call-insights-upload-service")
                .process(this::createMultipartToSendToInsights)
                .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
                .setHeader("x-rh-identity", method(MainRouteBuilder.class, "getRHIdentity(${header.x-rh-identity}, ${header.CamelFileName}, ${headers})"))
                .removeHeaders("Camel*")
                .to("http4://" + uploadHost + "/api/ingress/v1/upload")
                .to("log:INFO?showBody=true&showHeaders=true")
                .end();

        from("kafka:" + kafkaHost + "?topic={{insights.kafka.upload.topic}}&brokers=" + kafkaHost + "&autoOffsetReset=latest&autoCommitEnable=true")
                .id("kafka-upload-message")
                .unmarshal().json(JsonLibrary.Jackson, FilePersistedNotification.class)
                .filter(simple("'{{insights.service}}' == ${body.getService}"))
                .to("direct:download-file");

        from("direct:download-file")
                .id("download-file")
                .setHeader("Exchange.HTTP_URI", simple("${body.url}"))
                .process(this::extractAndEnrichRHIdentityFromNotification)
                .setBody(constant(""))
                .to("http4://oldhost")
                .removeHeader("Exchange.HTTP_URI")
                .to("direct:unzip-file");

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
                .doTry()
                    .transform().method("calculator", "calculate(${body}, ${header.MA_metadata})")
                    .log("Message to send to AMQ : ${body}")
                    .to("jms:queue:inputDataModel")
                .endDoTry()
                .doCatch(Exception.class)
                    .to("log:error?showCaughtException=true&showStackTrace=true")
                    .setBody(simple("Exception on parsing Cloudforms file"))
                .end();
    }

    private void extractAndEnrichRHIdentityFromNotification(Exchange exchange) throws IOException {
        FilePersistedNotification filePersistedNotification = exchange.getIn().getBody(FilePersistedNotification.class);
        String identity_json = new String(Base64.getDecoder().decode(filePersistedNotification.getB64_identity()));
        RHIdentity rhIdentity = new ObjectMapper().reader().forType(RHIdentity.class).withRootName("identity").readValue(identity_json);

        rhIdentity.getInternal().forEach((key,value) -> {
            Map header = exchange.getIn().getHeader("MA_metadata", new HashMap<String, String>(), Map.class);
            header.put(key, value);
            exchange.getIn().setHeader("MA_metadata", header);
        });
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

        String file = exchange.getIn().getBody(String.class);
        multipartEntityBuilder.addPart("file", new ByteArrayBody(file.getBytes(), ContentType.create(mimeType), exchange.getIn().getHeader(Exchange.FILE_NAME, String.class)));
        exchange.getIn().setBody(multipartEntityBuilder.build());
    }

    public String getRHIdentity(String x_rh_identity_json, String filename, Map<String, Object> headers) throws IOException {
        RHIdentity rhidentity;
        if (x_rh_identity_json != null) {
            rhidentity = new ObjectMapper().reader().withRootName("identity").readValue(new JsonFactory().createParser(x_rh_identity_json), RHIdentity.class);
        } else {
          rhidentity = new RHIdentity();  
        }
        
        // we add all properties defined on the Insights Properties, that we should have as Headers of the message
        insightsProperties.forEach(e -> rhidentity.getInternal().put(e, ((Map<String,Object>) headers.get("MA_metadata")).get(e).toString()));

        rhidentity.getInternal().put("filename", filename);
        String rhIdentity_json = "";
        try {
            rhIdentity_json = new ObjectMapper().writer().withRootName("identity").writeValueAsString(RHIdentity.builder()
                    .account_number(accountNumber)
                    .internal(rhidentity.getInternal())
                    .build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(rhIdentity_json.getBytes());
    }

    private Predicate isZippedFile(String extension) {
        return exchange -> {
            boolean zipContentType = isZipContentType(exchange);
            String filename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
            boolean zipExtension = extension.equalsIgnoreCase(filename.substring(filename.length() - extension.length()));
            return zipContentType && zipExtension;
        };
    }
    
    private boolean isZipContentType(Exchange exchange) {
        return "application/zip".equalsIgnoreCase(exchange.getMessage().getHeader(CustomizedMultipartDataFormat.CONTENT_TYPE).toString());
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
