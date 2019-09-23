package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.jboss.xavier.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("http4:{{insights.upload.host}}/api/ingress/v1/upload|direct:mark-analysis-fail")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectInsightsTest {
    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:http4:{{insights.upload.host}}/api/ingress/v1/upload")
    private MockEndpoint mockInsightsServiceHttp4;

    @EndpointInject(uri="mock:direct:mark-analysis-fail")
    private MockEndpoint mockMarkAnalysis;

    @Autowired
    MainRouteBuilder routeBuilder;

    @Test
    public void mainRouteBuilder_routeDirectInsights_ContentGiven_ShouldStoreinLocalFile() throws Exception {
        //Given

        String body = "this is a test body";
        String filename = "testfilename.txt";
        String customerid = "CID90765";
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", customerid);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, "30");

        Map<String,Object> headers = new HashMap<>();
        headers.put("CamelFileName", filename);
        headers.put(MainRouteBuilder.MA_METADATA, metadata);

        String rhidentity = "{\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}";
        headers.put("x-rh-identity", Base64.getEncoder().encodeToString(rhidentity.getBytes(StandardCharsets.UTF_8)));

        camelContext.getRouteDefinition("call-insights-upload-service").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after().setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"));
            }
        });

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockInsightsServiceHttp4.expectedMessageCount(1);

        //When
        camelContext.start();
        camelContext.startRoute("call-insights-upload-service");
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:insights", body, headers );

        //Then
        mockInsightsServiceHttp4.assertIsSatisfied();

        HttpEntity bodyResult = mockInsightsServiceHttp4.getExchanges().get(0).getIn().getBody(HttpEntity.class);
        String receivedBody = IOUtils.toString(bodyResult.getContent(), Charset.forName("UTF-8"));
        assertThat(receivedBody.indexOf(body)).isGreaterThanOrEqualTo(0);

        String expectedRHIdentity = routeBuilder.getRHIdentity(Base64.getEncoder().encodeToString(rhidentity.getBytes(StandardCharsets.UTF_8)), filename, headers);
        assertThat(mockInsightsServiceHttp4.getExchanges().get(0).getIn().getHeader("x-rh-identity", String.class)).isEqualToIgnoringCase(expectedRHIdentity);

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectInsights_UploadErrorGiven_ShouldMarkAnalysisAsFail() throws Exception {
        String body = "this is a test body";
        String filename = "testfilename.txt";
        String customerid = "CID90765";
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", customerid);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, "30");

        Map<String,Object> headers = new HashMap<>();
        headers.put("CamelFileName", filename);
        headers.put(MainRouteBuilder.MA_METADATA, metadata);

        String rhidentity = "{\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}";
        headers.put("x-rh-identity", Base64.getEncoder().encodeToString(rhidentity.getBytes(StandardCharsets.UTF_8)));

        camelContext.getRouteDefinition("call-insights-upload-service").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after().setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"));
            }
        });

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockMarkAnalysis.expectedMessageCount(1);

        //When
        camelContext.start();
        camelContext.startRoute("call-insights-upload-service");
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:insights", body, headers );

        //Then
        mockMarkAnalysis.assertIsSatisfied();

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectInsights_UploadSuccessGiven_ShouldNOTMarkAnalysisAsFail() throws Exception {
        String body = "this is a test body";
        String filename = "testfilename.txt";
        String customerid = "CID90765";
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", customerid);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, "30");

        Map<String,Object> headers = new HashMap<>();
        headers.put("CamelFileName", filename);
        headers.put(MainRouteBuilder.MA_METADATA, metadata);

        String rhidentity = "{\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}";
        headers.put("x-rh-identity", Base64.getEncoder().encodeToString(rhidentity.getBytes(StandardCharsets.UTF_8)));

        camelContext.getRouteDefinition("call-insights-upload-service").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after().setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"));
            }
        });

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockMarkAnalysis.expectedMessageCount(0);

        //When
        camelContext.start();
        camelContext.startRoute("call-insights-upload-service");
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:insights", body, headers );

        //Then
        mockMarkAnalysis.assertIsSatisfied();

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectInsights_XRHIdentityHeaderMissingGiven_ShouldMarkAnalysisAsFail() throws Exception {
        String body = "this is a test body";
        String filename = "testfilename.txt";
        String customerid = "CID90765";
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", customerid);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, "30");

        Map<String,Object> headers = new HashMap<>();
        headers.put("CamelFileName", filename);
        headers.put(MainRouteBuilder.MA_METADATA, metadata);

        camelContext.getRouteDefinition("call-insights-upload-service").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after().setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"));
            }
        });

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockMarkAnalysis.expectedMessageCount(1);

        //When
        camelContext.start();
        camelContext.startRoute("call-insights-upload-service");
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:insights", body, headers );

        //Then
        mockMarkAnalysis.assertIsSatisfied();

        camelContext.stop();
    }


}
