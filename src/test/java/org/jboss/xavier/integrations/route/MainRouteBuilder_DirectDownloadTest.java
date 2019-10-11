package org.jboss.xavier.integrations.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.aws.s3.S3Constants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.commons.codec.binary.Base64;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@MockEndpointsAndSkip("http4:oldhost|direct:unzip-file")
public class MainRouteBuilder_DirectDownloadTest extends XavierCamelTest {
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

        mockUnzipFile.expectedMessageCount(1);

        mockOldHost.expectedHeaderReceived("CamelHttpUri", "http://dummyurl.com");

        camelContext.getRouteDefinition("download-file").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveByToUri("http4:.*").after()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"))
                        .setBody(exchange -> this.getClass().getClassLoader().getResourceAsStream("cloudforms-export-v1_0_0.json"));
            }
        });
        camelContext.getRouteDefinition("store-in-s3").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveById("set-s3-key")
                        .replace().process(e -> e.getIn().setHeader(S3Constants.KEY, "S3KEY123"));
            }
        });

        //When
        camelContext.start();
        camelContext.startRoute("download-file");
        camelContext.startRoute("process-file");
        camelContext.startRoute("store-in-s3");

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
        assertThat(analysisService.findByOwnerAndId(analysisModel.getOwner(), analysisModel.getId()).getPayloadStorageId()).isEqualToIgnoringCase("S3KEY123");
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectDownloadFile_HTTPErrorDownloadingFileGiven_ShouldMarkAnalysisAsFailed() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");

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
