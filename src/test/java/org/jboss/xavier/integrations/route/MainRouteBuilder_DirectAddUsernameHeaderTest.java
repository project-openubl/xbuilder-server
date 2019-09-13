package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectAddUsernameHeaderTest {
    @Autowired
    CamelContext camelContext;

    @Test
    public void mainRouteBuilder_routeDirectAddUsernameHeader_ContentGiven_ShouldAddHeaderInExchange() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("add-username-header");

        String x_rh_identity= Base64.getEncoder().encodeToString("{\"entitlements\":{\"insights\":{\"is_entitled\":true},\"openshift\":{\"is_entitled\":true},\"smart_management\":{\"is_entitled\":false},\"hybrid_cloud\":{\"is_entitled\":true}},\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}".getBytes());
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-rh-identity", x_rh_identity);

        Exchange result = camelContext.createProducerTemplate().request("direct:add-username-header",  exchange -> {
            exchange.getIn().setBody(null);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(result.getIn().getHeader("analysisUsername")).isEqualTo("mrizzi@redhat.com");
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectAddUsernameHeader_NoContentGiven_ShouldAddEmptyHeaderInExchange() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("add-username-header");

        Exchange result = camelContext.createProducerTemplate().request("direct:add-username-header",  exchange -> {
            exchange.getIn().setBody(null);
            exchange.getIn().setHeaders(new HashMap<String, Object>());
        });

        //Then
        assertThat(result.getIn().getHeader("analysisUsername")).isEqualTo("");
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectAddUsernameHeader_MissingUsernameContentGiven_ShouldAddEmptyHeaderInExchange() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("add-username-header");

        String x_rh_identity= Base64.getEncoder().encodeToString("{\"entitlements\":{\"insights\":{\"is_entitled\":true},\"openshift\":{\"is_entitled\":true},\"smart_management\":{\"is_entitled\":false},\"hybrid_cloud\":{\"is_entitled\":true}},\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}".getBytes());
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-rh-identity", x_rh_identity);

        Exchange result = camelContext.createProducerTemplate().request("direct:add-username-header",  exchange -> {
            exchange.getIn().setBody(null);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(result.getIn().getHeader("analysisUsername")).isEqualTo("");
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectAddUsernameHeader_MissingUserContentGiven_ShouldAddEmptyHeaderInExchange() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("add-username-header");

        String x_rh_identity= Base64.getEncoder().encodeToString("{\"entitlements\":{\"insights\":{\"is_entitled\":true},\"openshift\":{\"is_entitled\":true},\"smart_management\":{\"is_entitled\":false},\"hybrid_cloud\":{\"is_entitled\":true}},\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"type\":\"User\"}}".getBytes());
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-rh-identity", x_rh_identity);

        Exchange result = camelContext.createProducerTemplate().request("direct:add-username-header",  exchange -> {
            exchange.getIn().setBody(null);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(result.getIn().getHeader("analysisUsername")).isEqualTo("");
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_routeDirectAddUsernameHeader_BadContentGiven_ShouldAddEmptyHeaderInExchange() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("add-username-header");

        String x_rh_identity= Base64.getEncoder().encodeToString("BadContentGiven".getBytes());
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-rh-identity", x_rh_identity);

        Exchange result = camelContext.createProducerTemplate().request("direct:add-username-header",  exchange -> {
            exchange.getIn().setBody(null);
            exchange.getIn().setHeaders(headers);
        });

        //Then
        assertThat(result.getIn().getHeader("analysisUsername")).isEqualTo("");
        camelContext.stop();
    }
}
