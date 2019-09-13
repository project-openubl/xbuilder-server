package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.DecisionServerHelper;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class XmlRoutes_RouteMaTest {
    @Autowired
    CamelContext camelContext;

    @Inject
    AnalysisService analysisService;

    @SpyBean
    DecisionServerHelper decisionServerHelper;

    public void modifyFromAndWeaveDecisionServer() throws Exception {
        camelContext.getRouteDefinition("route-ma").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:route-ma");
                mockEndpointsAndSkip("direct:decisionserver");
                weaveById("route-ma-decisionserver")
                        .after()
                        .process(exchange -> exchange.getIn().setBody(IOUtils.resourceToString("kie-server-response-initialcostsavingsreport.xml", StandardCharsets.UTF_8, XmlRoutes_RouteMaTest.class.getClassLoader())))
                        .unmarshal().xstream();
            }
        });
    }

    @Test
    public void xmlroutes_directInputDataModel_InputDataModelGiven_ShouldReportDecisionServerHelperValues() throws Exception
    {
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        assertThat(analysisModel.getInitialSavingsEstimationReportModel()).isNull();

        modifyFromAndWeaveDecisionServer();

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        camelContext.start();
        camelContext.startRoute("route-ma");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, "fichero.txt");

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:route-ma", getInputDataModelSample(analysisModel.getId()), headers);

        assertThat(analysisService.findByOwnerAndId("user name", analysisModel.getId()).getInitialSavingsEstimationReportModel()).isNotNull();

        camelContext.stop();
    }

    @Test
    public void xmlroutes_directInputDataModel_ErrorInsideExtractReportGiven_ShouldMarkAnalysisAsFailed() throws Exception {
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        assertThat(analysisModel.getInitialSavingsEstimationReportModel()).isNull();

        doThrow(new IllegalArgumentException("Dummy error")).when(decisionServerHelper).extractInitialSavingsEstimationReportModel(any());

        modifyFromAndWeaveDecisionServer();

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        camelContext.start();
        camelContext.startRoute("route-ma");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, "fichero.txt");

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:route-ma", getInputDataModelSample(analysisModel.getId()), headers);

        assertThat(analysisService.findByOwnerAndId("user name", analysisModel.getId()).getStatus()).isEqualToIgnoringCase("FAILED");

        camelContext.stop();
    }

    @Test
    public void xmlroutes_directInputDataModel_BadHttpResponseReceivedGiven_ShouldMarkAnalysisAsFailed() throws Exception {
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        assertThat(analysisModel.getInitialSavingsEstimationReportModel()).isNull();

        camelContext.getRouteDefinition("route-ma").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith("direct:route-ma");
            }
        });
        camelContext.getRouteDefinition("decision-server-rest").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                mockEndpointsAndSkip("http.*");
                weaveById("route-to-decision-server-rest")
                        .after()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500"));
            }
        });

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        camelContext.start();
        camelContext.startRoute("route-ma");
        camelContext.startRoute("decision-server-rest");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, "fichero.txt");

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:route-ma", getInputDataModelSample(analysisModel.getId()), headers);

        assertThat(analysisService.findByOwnerAndId("user name", analysisModel.getId()).getStatus()).isEqualToIgnoringCase("FAILED");

        camelContext.stop();
    }



    private UploadFormInputDataModel getInputDataModelSample(Long analysisId) {
        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer hypervisor = 1;
        Long totaldiskspace = 1000L;
        Integer sourceproductindicator = 1;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;
        return new UploadFormInputDataModel(customerId, fileName, hypervisor, totaldiskspace, sourceproductindicator, year1hypervisorpercentage, year2hypervisorpercentage, year3hypervisorpercentage, growthratepercentage, analysisId);
    }
}
