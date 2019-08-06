package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.apache.commons.codec.binary.Base64;
import org.jboss.xavier.integrations.route.model.notification.FilePersistedNotification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

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

    @Test
    public void mainRouteBuilder_DirectDownloadFile_PersistedNotificationGiven_ShouldCallFileWithGivenHeaders() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockUnzipFile.expectedMessageCount(1);

        mockOldHost.expectedHeaderReceived("CamelHttpUri", "http://dummyurl.com");

        //When
        camelContext.start();
        camelContext.startRoute("download-file");
        Map<String, Object> headers = new HashMap<>();
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("dummy", "CID1234");
        metadata.put("analysisId", "3");
        headers.put("MA_metadata", metadata);

        String rhidentity = "{\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}";
        FilePersistedNotification body = FilePersistedNotification.builder().url("http://dummyurl.com").category("cat").service("xavier").b64_identity(mainRouteBuilder.getRHIdentity(Base64.encodeBase64String(rhidentity.getBytes(StandardCharsets.UTF_8)), "ficherito.txt", headers)).build();

        camelContext.createProducerTemplate().sendBody("direct:download-file", body);

        //Then
        mockOldHost.assertIsSatisfied();
        assertThat(mockOldHost.getExchanges().get(0).getIn().getHeader("MA_metadata", Map.class).get("dummy")).isEqualTo("CID1234");
        assertThat(mockOldHost.getExchanges().get(0).getIn().getHeader("MA_metadata", Map.class).get("auth_time")).isEqualTo("0");
        mockUnzipFile.assertIsSatisfied();

        camelContext.stop();
    }

}
