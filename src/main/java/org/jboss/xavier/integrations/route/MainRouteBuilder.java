package org.jboss.xavier.integrations.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Attachment;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.jboss.xavier.integrations.migrationanalytics.input.InputDataModel;
import org.jboss.xavier.integrations.route.dataformat.CustomizedMultipartDataFormat;
import org.jboss.xavier.integrations.route.model.RHIdentity;
import org.jboss.xavier.integrations.route.model.cloudforms.CloudFormAnalysis;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A Camel Java8 DSL Router
 */
@Component
public class MainRouteBuilder extends RouteBuilder {

    @Value("${insights.upload.host}")
    private String uploadHost;

    @Value("${insights.kafka.host}")
    private String kafkaHost;

    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    
    @Value("${insights.upload.mimetype}")
    private String mimeType;
    
    @Value("${insights.upload.accountnumber}")
    private String accountNumber;
    
    @Value("${insights.upload.origin}")
    private String origin;

    public void configure() {
        getContext().setTracing(true);

/*
        restConfiguration()
                .component("servlet")
                .contextPath("/");
*/

        rest()
                .post("/upload/{customerID}")
                    .id("uploadAction")
                    .bindingMode(RestBindingMode.off)
                    .consumes("multipart/form-data")
                    .produces("")
                    .to("direct:upload")
/*                .get("/health")
                    .to("direct:health")*/;

        from("direct:upload")
                .unmarshal(new CustomizedMultipartDataFormat())
                .split()
                    .attachments()
                    .process(processMultipart())
                    .choice()
                        .when(isZippedFile())
                            .split(new ZipSplitter())
                            .streaming()
                            .to("direct:store")
                        .endChoice()
                        .otherwise()
                            .to("direct:store");

        from("direct:store")
                .convertBodyTo(String.class)
                .to("file:./upload")
                .to("direct:insights");

        from("direct:insights")
                .process(exchange -> {
                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                    multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
                    String filename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
                    exchange.getIn().setHeader(Exchange.FILE_NAME, filename);

                    String file = exchange.getIn().getBody(String.class);
                    multipartEntityBuilder.addPart("upload", new ByteArrayBody(file.getBytes(), ContentType.create(mimeType), filename));
                    exchange.getIn().setBody(multipartEntityBuilder.build());
                })
                .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
                .setHeader("x-rh-identity", method(MainRouteBuilder.class, "getRHIdentity(${header.customerid}, ${header.CamelFileName})"))
                .setHeader("x-rh-insights-request-id", constant(getRHInsightsRequestId()))
                .removeHeaders("Camel*")
                .to("http4://" + uploadHost + "/api/ingress/v1/upload")
                .end();

        from("kafka:" + kafkaHost + "?topic=platform.upload.testareno&brokers=" + kafkaHost + "&autoOffsetReset=latest&autoCommitEnable=true")
                .process(exchange -> {
                    String messageKey = "";
                    if (exchange.getIn() != null) {
                        Message message = exchange.getIn();
                        Integer partitionId = (Integer) message.getHeader(KafkaConstants.PARTITION);
                        String topicName = (String) message.getHeader(KafkaConstants.TOPIC);
                        if (message.getHeader(KafkaConstants.KEY) != null)
                            messageKey = (String) message.getHeader(KafkaConstants.KEY);
                        Object data = message.getBody();

                        System.out.println("topicName :: " + topicName +
                                " partitionId :: " + partitionId +
                                " messageKey :: " + messageKey +
                                " message :: "+ data + "\n");
                    }
                })
                .unmarshal().json(JsonLibrary.Jackson, FilePersistedNotification.class)
                .to("direct:download-from-S3");


        from("direct:download-from-S3")
                .setHeader("Exchange.HTTP_URI", simple("${body.url}"))
                .process( exchange -> {
                    FilePersistedNotification notif_body = exchange.getIn().getBody(FilePersistedNotification.class);
                    String identity_json = new String(Base64.getDecoder().decode(notif_body.getB64_identity()));
                    RHIdentity rhIdentity = new ObjectMapper().reader().forType(RHIdentity.class).withRootName("identity").readValue(identity_json);
                    exchange.getIn().setHeader("customerid", rhIdentity.getInternal().get("customerid"));
                    exchange.getIn().setHeader("filename", rhIdentity.getInternal().get("filename"));
                    exchange.getIn().setHeader("origin", rhIdentity.getInternal().get("origin"));
                })
                .filter().method(MainRouteBuilder.class, "filterMessages")
                .setBody(constant(""))
                .to("http4://oldhost")
                .removeHeader("Exchange.HTTP_URI")
                .convertBodyTo(String.class)
                .to("direct:parse");

        from("direct:parse")
                .unmarshal().json(JsonLibrary.Jackson, CloudFormAnalysis.class)
                .process(exchange -> {
                    int numberofhosts = exchange.getIn().getBody(CloudFormAnalysis.class).getDatacenters()
                            .stream()
                            .flatMap(e -> e.getEmsClusters().stream())
                            .mapToInt(t -> t.getHosts().size())
                            .sum();
                    long totalspace = exchange.getIn().getBody(CloudFormAnalysis.class).getDatacenters()
                            .stream()
                            .flatMap(e-> e.getDatastores().stream())
                            .mapToLong(t -> t.getTotalSpace())
                            .sum();
                    exchange.getIn().setHeader("numberofhosts",String.valueOf(numberofhosts));
                    exchange.getIn().setHeader("totaldiskspace", String.valueOf(totalspace));
                })
                .process(exchange ->
                {
                    InputDataModel inputDataModel = new InputDataModel();
                    inputDataModel.setCustomerId(exchange.getIn().getHeader("customerid").toString());
                    inputDataModel.setFileName(getFilename(exchange.getIn().getHeader("filename").toString()));
                    inputDataModel.setNumberOfHosts(Integer.parseInt((exchange.getMessage().getHeader("numberofhosts").toString())));
                    inputDataModel.setTotalDiskSpace(Long.parseLong(exchange.getMessage().getHeader("totaldiskspace").toString()));
                    exchange.getMessage().setBody(inputDataModel);
                })
                .log("Message to send to AMQ : ${body}")
//                .marshal().json()
                .to("jms:queue:inputDataModel");
    }

    private String getFilename(String filename) {
        return format.format(new Date()) + "-" + filename;
    }

    public boolean filterMessages(Exchange exchange) {
        String originHeader = exchange.getIn().getHeader("origin", String.class);
        System.out.println("Origin header : " + originHeader + " env var : " + origin);
        return (originHeader != null && originHeader.equalsIgnoreCase(origin));
    }

    private String getRHInsightsRequestId() {
        // 52df9f748eabcfea
        return UUID.randomUUID().toString();
    }

    public String getRHIdentity(String customerid, String filename) {
        // '{"identity": {"account_number": "12345", "internal": {"org_id": "54321"}}}'
        Map<String,String> internal = new HashMap<>();
        internal.put("customerid", customerid);
        internal.put("filename", filename);
        internal.put("origin", origin);
        String rhIdentity_json = "";
        try {
            rhIdentity_json = new ObjectMapper().writer().withRootName("identity").writeValueAsString(RHIdentity.builder()
                    .account_number(accountNumber)
                    .internal(internal)
                    .build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("---------- RHIdentity : " + rhIdentity_json);
        return Base64.getEncoder().encodeToString(rhIdentity_json.getBytes());
    }

    private Predicate isZippedFile() {
        return exchange -> "application/zip".equalsIgnoreCase(exchange.getMessage().getHeader("part_contenttype").toString());
    }

    private Processor processMultipart() {
        return exchange -> {
            DataHandler dataHandler = exchange.getIn().getBody(Attachment.class).getDataHandler();
            exchange.getIn().setHeader(Exchange.FILE_NAME, dataHandler.getName());
            exchange.getIn().setHeader("part_contenttype", dataHandler.getContentType());
            exchange.getIn().setHeader("part_name", dataHandler.getName());
            exchange.getIn().setBody(dataHandler.getInputStream());
        };
    }


}
