package org.jboss.xavier.integrations.route;

import org.jboss.xavier.Application;
import org.jboss.xavier.integrations.jpa.service.FlagAssessmentService;
import org.jboss.xavier.integrations.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.mockito.Mockito.verify;


@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XmlRoutes_RestMappingTest extends XavierCamelTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${camel.component.servlet.mapping.context-path}")
    String camel_context;

    @SpyBean
    private FlagAssessmentService flagAssessmentService;

    @Before
    public void setup() {
        camel_context = camel_context.substring(0, camel_context.indexOf("*"));
    }

    @Test
    public void xmlRouteBuilder_RestMapping_ShouldCallFindFlagAssessment_FindAll() throws Exception {
        //Given

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("mappings-flag-assessment-findAll");


        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        restTemplate.exchange(camel_context + "mappings/flag-assessment", HttpMethod.GET, entity, String.class);

        //Then
        verify(flagAssessmentService).findAll();
        camelContext.stop();
    }

}
