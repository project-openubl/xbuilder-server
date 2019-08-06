package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.junit.Before;
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
@MockEndpointsAndSkip("direct:decisionserver")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class XmlRoutes_RouteMaTest {
    @Autowired
    CamelContext camelContext;

    @Inject
    AnalysisService analysisService;

    @Before
    public void setup() throws Exception {
        camelContext.getRouteDefinition("route-ma").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith("direct:route-ma");
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
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name");
        assertThat(analysisModel.getInitialSavingsEstimationReportModel()).isNull();

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        camelContext.start();
        camelContext.startRoute("route-ma");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, "fichero.txt");

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:route-ma", getInputDataModelSample(analysisModel.getId()), headers);

        assertThat(analysisService.findById(analysisModel.getId()).getInitialSavingsEstimationReportModel()).isNotNull();

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
