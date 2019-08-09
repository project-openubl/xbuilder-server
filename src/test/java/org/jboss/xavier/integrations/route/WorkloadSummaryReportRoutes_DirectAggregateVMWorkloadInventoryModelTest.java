package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadInventoryReportRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:calculate-workloadsummaryreportmodel")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class WorkloadSummaryReportRoutes_DirectAggregateVMWorkloadInventoryModelTest {
    @Inject
    CamelContext camelContext;

    @EndpointInject(uri = "mock:direct:calculate-workloadsummaryreportmodel")
    private MockEndpoint mockCalculateVMWorkloadInventoryModel;

    @Autowired
    WorkloadInventoryReportRepository workloadInventoryReportRepository;

    @Autowired
    AnalysisRepository analysisRepository;

    private Long analysisId;

    @Before
    public void setup()
    {
        AnalysisModel analysisModel = new AnalysisModel();
        analysisModel = analysisRepository.save(analysisModel);
        analysisId = analysisModel.getId();
    }

    @Test
    public void DirectAggregateVMWorkloadInventoryModel_ShouldWaitForWorkloadInventoryReportModelInTheDB() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        //When
        camelContext.start();
        camelContext.startRoute("aggregate-vmworkloadinventory");

        int collectionSize = 4;
        Collection<VMWorkloadInventoryModel> vmWorkloadInventoryModels = new ArrayList<>(collectionSize);
        IntStream.range(0, collectionSize).forEach(value -> vmWorkloadInventoryModels.add(new VMWorkloadInventoryModel()));

        Map<String, String> metadata = new HashMap<>();
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        CompletableFuture<Object> responseFuture = camelContext.createProducerTemplate().asyncRequestBodyAndHeaders("direct:aggregate-vmworkloadinventory", vmWorkloadInventoryModels, headers);

        AnalysisModel analysisModel = new AnalysisModel();
        analysisModel.setId(analysisId);
        IntStream.range(0, collectionSize).forEach(value -> {
            try {
                Thread.sleep(7000);
                WorkloadInventoryReportModel workloadInventoryReportModel = new WorkloadInventoryReportModel();
                workloadInventoryReportModel.setAnalysis(analysisModel);
                System.out.println("Saved WorkloadInventoryReportModel with ID #" + workloadInventoryReportRepository.save(workloadInventoryReportModel).getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //Then
        Object response = responseFuture.join();
        assertThat(response).isInstanceOf(Collection.class);
        assertThat(mockCalculateVMWorkloadInventoryModel.getExchanges().size()).isEqualTo(1);

        camelContext.stop();
    }

}
