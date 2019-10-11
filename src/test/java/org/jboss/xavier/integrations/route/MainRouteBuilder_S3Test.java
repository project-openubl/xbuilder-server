package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.s3.S3Constants;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.UUID;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_S3Test {
    @Inject
    CamelContext camelContext;

    @Test
    @Ignore
    public void s3Test() throws Exception {
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String keyValue = UUID.randomUUID().toString();
                System.out.println(String.format("UUID [%s]", keyValue));
                from("file:///home/jonathan/Downloads?fileName=cfme_inventory_0.json&noop=true").routeId("s3route")
                        .convertBodyTo(byte[].class)
                        .setHeader(S3Constants.CONTENT_LENGTH, simple("${in.header.CamelFileLength}"))
                        .setHeader(S3Constants.KEY, simple(keyValue))
                        .setHeader(S3Constants.CONTENT_DISPOSITION, simple("attachment;filename=\"${header.CamelFileNameOnly}\""))
                        .setBody(e -> e.getIn().getBody(String.class).replace("5.11.0", "9.9.9.9"))
                        .to("aws-s3:xavier-dev?region=US_EAST_1&accessKey=accesskey&secretKey=RAW(secretkey)&deleteAfterWrite=false")
                        .log("FILE UPLOADED");

                String uri = "aws-s3:xavier-dev?region=US_EAST_1&accessKey=accesskey&secretKey=RAW(secretkey)&fileName=" + keyValue + "&deleteAfterRead=false";
                from(uri).routeId("s3route-consumer")
                        .process(e -> {
                            String camelAwsS3ContentDisposition = e.getIn().getHeader("CamelAwsS3ContentDisposition", String.class);
                            String filename = camelAwsS3ContentDisposition.substring(camelAwsS3ContentDisposition.indexOf("filename=") + 10, camelAwsS3ContentDisposition.length() - 1);
                            e.getIn().setHeader("filename", filename );
                        })
                        .to("file:/home/jonathan/Documents?fileName=${header.filename}");
            }
        });
        camelContext.start();
        camelContext.startRoute("s3route");

        Thread.sleep(5000);
        camelContext.startRoute("s3route-consumer");

        Thread.sleep(2000);

        camelContext.stop();
    }

}
