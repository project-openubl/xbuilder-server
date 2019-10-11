package org.jboss.xavier.integrations.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

@MockEndpointsAndSkip("kafka:*|direct:download-file")
public class MainRouteBuilder_KafkaTest extends XavierCamelTest {

    @EndpointInject(uri = "mock:direct:download-file")
    private MockEndpoint mockUploadFile;

    @Before
    public void setup() throws Exception {
        camelContext.getRouteDefinition("kafka-upload-message").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith("direct:kafka");
            }
        });
    }

    @Test
    public void mainRouteBuilder_Kafka_ContentWithSeveralFilesGiven_ShouldReturnSameNumberOfMessages() throws Exception {
        //Given
        mockUploadFile.expectedMessageCount(2);

        //When
        camelContext.start();
        camelContext.startRoute("kafka-upload-message");
        String body = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("platform.upload.xavier.json"), Charset.forName("UTF-8"));

        camelContext.createProducerTemplate().sendBodyAndHeader("direct:kafka", body, "Content-Type", "multipart/mixed");
        camelContext.createProducerTemplate().sendBodyAndHeader("direct:kafka", body.replaceAll("xavier", "javier"), "Content-Type", "multipart/mixed");
        camelContext.createProducerTemplate().sendBodyAndHeader("direct:kafka", body.replaceAll("cloudforms", "test"), "Content-Type", "multipart/mixed");

        //Then
        mockUploadFile.assertIsSatisfied();

        camelContext.stop();
    }

}
