package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.integrations.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:upload")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
@ActiveProfiles("test")
public class MainRouteBuilder_RestUploadTest {
    @Autowired
    CamelContext camelContext;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Value("${camel.component.servlet.mapping.context-path}")
    String camel_context;
    
    @Before
    public void setup() {
        camel_context = camel_context.substring(0, camel_context.indexOf("*"));
    }
   
    @Test
    public void mainRouteBuilder_routeRestUpload_ContentGiven_ShouldStoreinLocalFile() throws Exception {
        //Given
                
        String body = "{ \"body\" : \"this is a test body\" }";
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("rest-upload");
        ResponseEntity<String> answer = restTemplate.postForEntity(camel_context + "upload", body, String.class);

        //Then
        assertThat(answer).isNotNull();
        assertThat(answer.getBody()).isEqualToIgnoringCase(body);
        camelContext.stop();
    }
    

    
}