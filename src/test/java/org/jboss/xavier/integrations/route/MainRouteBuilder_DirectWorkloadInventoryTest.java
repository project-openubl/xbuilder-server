package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@MockEndpointsAndSkip("direct:decisionserver")
public class MainRouteBuilder_DirectWorkloadInventoryTest extends XavierCamelTest {
    @Inject
    AnalysisService analysisService;

    @Before
    public void setup() throws Exception {
        camelContext.getRouteDefinition("extract-vmworkloadinventory").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith("direct:vm-workload-inventory");
                weaveById("workload-decisionserver")
                        .after()
                        .process(exchange -> exchange.getIn().setBody(IOUtils.resourceToString("kie-server-response-workloadinventoryreport.xml", StandardCharsets.UTF_8, MainRouteBuilder_DirectWorkloadInventoryTest.class.getClassLoader())))
                        .unmarshal().xstream();
            }
        });
    }

    @Test
    public void xmlroutes_directInputDataModel_InputDataModelGiven_ShouldReportDecisionServerHelperValues() throws Exception
    {
        camelContext.start();
        camelContext.startRoute("extract-vmworkloadinventory");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/zip");
        headers.put(Exchange.FILE_NAME, "fichero.zip");

        Map<String,Object> metadata = new HashMap<>();
        metadata.put("filename", "fichero.txt");
        metadata.put("dummy", "CID123");
        metadata.put("reportName", "report name");
        metadata.put("reportDescription", "report description");
        metadata.put("file", "fichero.zip");
        metadata.put(RouteBuilderExceptionHandler.ANALYSIS_ID, "1");
        headers.put(RouteBuilderExceptionHandler.MA_METADATA, metadata);

        Exchange testExchange = camelContext.createProducerTemplate().request("direct:vm-workload-inventory", exchange -> {
            exchange.getIn().setBody(getInputDataModelSample(1L));
            exchange.getIn().setHeaders(headers);
        });

        assertThat(testExchange.getIn().getBody(WorkloadInventoryReportModel.class)).isNotNull();

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
