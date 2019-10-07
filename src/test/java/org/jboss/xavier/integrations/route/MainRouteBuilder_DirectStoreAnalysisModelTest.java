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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:insights|file:*")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectStoreAnalysisModelTest {
    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:direct:insights")
    private MockEndpoint mockInsights;

    @Inject
    private AnalysisRepository analysisRepository;

    @Test
    public void mainRouteBuilder_routeDirectStore_ContentGiven_ShouldCreateRowInAnalysisModel() throws Exception {
        //Given

        String body = "this is a test body";
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockInsights.expectedBodiesReceived(body);

        //When
        camelContext.start();
        camelContext.startRoute("direct-store");
        camelContext.startRoute("analysis-model-creation");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, "fichero.txt");

        Map<String,String> metadata = new HashMap<>();
        metadata.put("filename", "fichero.txt");
        metadata.put("dummy", "CID123");
        metadata.put("reportName", "Name");
        metadata.put("reportDescription", "Description");
        metadata.put("file", "fichero.txt");
        headers.put(RouteBuilderExceptionHandler.MA_METADATA, metadata);
        headers.put(RouteBuilderExceptionHandler.USERNAME, "user name");

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:store", body, headers);

        //Then
        mockInsights.assertIsSatisfied();

        List<AnalysisModel> analysisModels = analysisRepository.findAll();
        assertThat(analysisModels.size()).isEqualTo(1);
        AnalysisModel analysisModel = analysisModels.get(0);
        assertThat(analysisModel.getReportName()).isEqualTo("Name");
        assertThat(analysisModel.getReportDescription()).isEqualTo("Description");
        assertThat(analysisModel.getPayloadName()).isEqualTo("fichero.txt");
        assertThat(analysisModel.getOwner()).isEqualTo("user name");
        assertThat((String) mockInsights.getExchanges().get(0).getIn().getHeader(RouteBuilderExceptionHandler.MA_METADATA, Map.class).get(RouteBuilderExceptionHandler.ANALYSIS_ID)).isEqualTo(analysisModels.get(0).getId().toString());
        camelContext.stop();
    }



}
