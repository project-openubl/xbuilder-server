package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.integrations.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.Charset;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("kafka:*|direct:download-file")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class}) 
@ActiveProfiles("test")
public class MainRouteBuilder_KafkaTest {
    @Autowired
    CamelContext camelContext;

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
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
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