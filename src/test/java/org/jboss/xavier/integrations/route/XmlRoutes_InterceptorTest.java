package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Route;
import org.apache.camel.component.rest.RestEndpoint;
import org.apache.camel.impl.InterceptSendToEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.integrations.jpa.service.*;
import org.jboss.xavier.integrations.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class XmlRoutes_InterceptorTest {

    @Autowired
    CamelContext camelContext;

    @Autowired
    private TestRestTemplate restTemplate;

    @SpyBean
    private UserService userService;

    @MockBean
    private InitialSavingsEstimationReportService initialSavingsEstimationReportService;

    @MockBean
    private WorkloadInventoryReportService workloadInventoryReportService;

    @MockBean
    private WorkloadService workloadService;

    @MockBean
    private FlagService flagService;

    @SpyBean
    private AnalysisService analysisService;

    @SpyBean
    private WorkloadSummaryReportService workloadSummaryReportService;

    @Value("${camel.component.servlet.mapping.context-path}")
    String camel_context;

    @Before
    public void setup() {
        camel_context = camel_context.substring(0, camel_context.indexOf("*"));
    }

    @Test
    public void xmlRouteBuilder_RestEndpoints_NoRHIdentityGiven_ShouldReturnForbidden() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        final AtomicInteger restEndpointsTested = new AtomicInteger(0);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);

        Supplier<Stream<Route>> streamRestRouteSupplier = () -> camelContext.getRoutes().stream()
                .filter(route -> route.getEndpoint() instanceof RestEndpoint);

        long expectedRestEndpointsTested = streamRestRouteSupplier.get().count();
        streamRestRouteSupplier.get()
                .forEach(route -> {
                    try {
                        camelContext.startRoute(route.getId());

                        Map<String, Object> variables = new HashMap<>();
                        Long one = 1L;
                        variables.put("id", one);

                        RestEndpoint restEndpoint = (RestEndpoint) route.getEndpoint();
                        String url = camel_context + restEndpoint.getPath();
                        if (restEndpoint.getUriTemplate() != null) url += restEndpoint.getUriTemplate();
                        ResponseEntity<String> result = restTemplate.exchange(
                                url,
                                HttpMethod.resolve(restEndpoint.getMethod().toUpperCase()),
                                new HttpEntity<>(null, null),
                                String.class,
                                variables);

                        //Then
                        assertThat(result).isNotNull();
                        assertThat(result.getStatusCodeValue()).isEqualByComparingTo(403);
                        assertThat(result.getBody()).isEqualTo("Forbidden");
                        verifyZeroInteractions(analysisService);
                        verifyZeroInteractions(initialSavingsEstimationReportService);
                        verifyZeroInteractions(workloadInventoryReportService);
                        verifyZeroInteractions(workloadSummaryReportService);
                        verifyZeroInteractions(workloadService);
                        verifyZeroInteractions(flagService);
                        restEndpointsTested.incrementAndGet();
                        camelContext.stopRoute(route.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        verify(userService, never()).isUserAllowedToAdministratorResources(anyString());
        assertThat(expectedRestEndpointsTested).isGreaterThanOrEqualTo(1);
        assertThat(restEndpointsTested.get()).isEqualTo(expectedRestEndpointsTested);
        camelContext.stop();
    }

    @Test
    public void xmlRouteBuilder_AuthorizedInterceptor_AdministrationEndpoints_GivenRHIdentity_and_UnauthorizedUser_ShouldReturnForbidden() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        final AtomicInteger restEndpointsTested = new AtomicInteger(0);

        //When
        camelContext.start();
        TestUtil.startUsernameRoutes(camelContext);

        Supplier<Stream<Route>> streamRestRouteSupplier = () -> camelContext.getRoutes().stream()
                .filter(route -> {
                    String endpointUri = route.getEndpoint().getEndpointUri();
                    return endpointUri.startsWith("rest://") && endpointUri.contains("administration");
                });

        long expectedRestEndpointsTested = streamRestRouteSupplier.get().count();
        streamRestRouteSupplier.get()
                .forEach(route -> {
                    try {
                        camelContext.startRoute(route.getId());

                        HttpHeaders headers = new HttpHeaders();
                        headers.set(TestUtil.HEADER_RH_IDENTITY, TestUtil.getBase64RHIdentity());
                        HttpEntity<String> entity = new HttpEntity<>(null, headers);

                        Map<String, Object> variables = new HashMap<>();
                        Long one = 1L;
                        variables.put("id", one);

                        Endpoint endpoint = route.getEndpoint();
                        if (route.getEndpoint() instanceof InterceptSendToEndpoint) {
                            InterceptSendToEndpoint interceptSendToEndpoint = (InterceptSendToEndpoint) route.getEndpoint();
                            endpoint = interceptSendToEndpoint.getDelegate();
                        }
                        RestEndpoint restEndpoint = (RestEndpoint) endpoint;
                        String url = camel_context + restEndpoint.getPath();
                        if (restEndpoint.getUriTemplate() != null) url += restEndpoint.getUriTemplate();
                        ResponseEntity<String> result = restTemplate.exchange(
                                url,
                                HttpMethod.resolve(restEndpoint.getMethod().toUpperCase()),
                                entity,
                                String.class,
                                variables);

                        //Then
                        assertThat(result).isNotNull();
                        assertThat(result.getStatusCodeValue()).isEqualByComparingTo(403);
                        assertThat(result.getBody()).isEqualTo("Forbidden");

                        restEndpointsTested.incrementAndGet();
                        camelContext.stopRoute(route.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        verify(userService, times((int) expectedRestEndpointsTested)).isUserAllowedToAdministratorResources(anyString());
        assertThat(expectedRestEndpointsTested).isGreaterThanOrEqualTo(1);
        assertThat(restEndpointsTested.get()).isEqualTo(expectedRestEndpointsTested);
        camelContext.stop();
    }

}
