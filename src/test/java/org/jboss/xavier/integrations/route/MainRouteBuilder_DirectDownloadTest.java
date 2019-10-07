package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.codec.binary.Base64;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("http4:oldhost|direct:unzip-file")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectDownloadTest {
    @Autowired
    CamelContext camelContext;

    @Autowired
    MainRouteBuilder mainRouteBuilder;

    @EndpointInject(uri = "mock:http4:oldhost")
    private MockEndpoint mockOldHost;

    @EndpointInject(uri = "mock:direct:unzip-file")
    private MockEndpoint mockUnzipFile;

    @Inject
    AnalysisService analysisService;

    @Test
    public void mainRouteBuilder_DirectDownloadFile_PersistedNotificationGiven_ShouldCallFileWithGivenHeaders() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockUnzipFile.expectedMessageCount(1);

        mockOldHost.expectedHeaderReceived("CamelHttpUri", "http://dummyurl.com");

        camelContext.getRouteDefinition("download-file").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after().setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"));
            }
        });

        //When
        camelContext.start();
        camelContext.startRoute("download-file");

        Map<String, Object> headers = new HashMap<>();
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", "CID1234");
        metadata.put(RouteBuilderExceptionHandler.ANALYSIS_ID,analysisModel.getId().toString());
        headers.put(RouteBuilderExceptionHandler.MA_METADATA, metadata);

        String rhidentityFrom3Scale = "{\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}";
        String x_rh_identity_base64 = Base64.encodeBase64String(rhidentityFrom3Scale.getBytes(StandardCharsets.UTF_8));
        String rhIdentity = mainRouteBuilder.getRHIdentity(x_rh_identity_base64, "ficherito.txt", headers);
        FilePersistedNotification body = FilePersistedNotification.builder().url("http://dummyurl.com").category("cat").service("xavier").b64_identity(rhIdentity).build();

        camelContext.createProducerTemplate().sendBody("direct:download-file", body);

        //Then
        mockOldHost.assertIsSatisfied();
        assertThat(mockOldHost.getExchanges().get(0).getIn().getHeader(RouteBuilderExceptionHandler.MA_METADATA, Map.class).get("dummy")).isEqualTo("CID1234");
        assertThat(mockOldHost.getExchanges().get(0).getIn().getHeader(RouteBuilderExceptionHandler.MA_METADATA, Map.class).get("auth_time")).isEqualTo("0");
        mockUnzipFile.assertIsSatisfied();
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectDownloadFile_HTTPErrorDownloadingFileGiven_ShouldMarkAnalysisAsFailed() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        camelContext.getRouteDefinition("download-file").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after().setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"));
            }
        });

        //When
        camelContext.start();
        camelContext.startRoute("download-file");

        Map<String, Object> headers = new HashMap<>();
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", "CID1234");
        metadata.put(RouteBuilderExceptionHandler.ANALYSIS_ID,analysisModel.getId().toString());
        headers.put(RouteBuilderExceptionHandler.MA_METADATA, metadata);

        String rhidentityFrom3Scale = "{\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}";
        String x_rh_identity_base64 = Base64.encodeBase64String(rhidentityFrom3Scale.getBytes(StandardCharsets.UTF_8));
        String rhIdentity = mainRouteBuilder.getRHIdentity(x_rh_identity_base64, "ficherito.txt", headers);
        FilePersistedNotification body = FilePersistedNotification.builder().url("http://dummyurl.com").category("cat").service("xavier").b64_identity(rhIdentity).build();

        camelContext.createProducerTemplate().sendBody("direct:download-file", body);

        //Then
        assertThat(analysisService.findByOwnerAndId("user name", analysisModel.getId()).getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.FAILED.toString());

        camelContext.stop();
    }

}
