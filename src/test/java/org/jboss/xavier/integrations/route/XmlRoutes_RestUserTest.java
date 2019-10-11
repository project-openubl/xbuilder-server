package org.jboss.xavier.integrations.route;

import org.jboss.xavier.Application;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
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
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XmlRoutes_RestUserTest extends XavierCamelTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${camel.component.servlet.mapping.context-path}")
    String camel_context;

    @SpyBean
    private AnalysisService analysisService;

    @Before
    public void setup() {
        camel_context = camel_context.substring(0, camel_context.indexOf("*"));
    }

    @Test
    public void mainRouteBuilder_RestUserWithAnalysis_ShouldCallCountByOwner() throws Exception {
        //Given
        when(analysisService.countByOwner("mrizzi@redhat.com")).thenReturn(2);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("get-user-info");

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "user", HttpMethod.GET, entity, String.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualToIgnoringCase("{\"firstTimeCreatingReports\":false}");
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_RestUserWithoutAnalysis_ShouldCallCountByOwner() throws Exception {
        //Given
        when(analysisService.countByOwner("mrizzi@redhat.com")).thenReturn(0);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);
        camelContext.startRoute("get-user-info");

        HttpHeaders headers = new HttpHeaders();
        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(camel_context + "user", HttpMethod.GET, entity, String.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualToIgnoringCase("{\"firstTimeCreatingReports\":true}");
        camelContext.stop();
    }

}
