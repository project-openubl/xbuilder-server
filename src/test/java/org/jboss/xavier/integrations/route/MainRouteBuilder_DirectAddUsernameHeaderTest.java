package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MainRouteBuilder_DirectAddUsernameHeaderTest extends XavierCamelTest {

    @Test
    public void mainRouteBuilder_routeDirectAddUsernameHeader_ContentGiven_ShouldAddHeaderInExchange() throws Exception {
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
