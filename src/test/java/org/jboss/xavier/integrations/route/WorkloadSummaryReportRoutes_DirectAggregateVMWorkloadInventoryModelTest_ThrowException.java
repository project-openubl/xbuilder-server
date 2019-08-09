package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.repository.AnalysisRepository;
import org.jboss.xavier.integrations.jpa.repository.WorkloadInventoryReportRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.isA;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:calculate-workloadsummaryreportmodel")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@TestPropertySource(properties = {"report.workload.summary.polling.delay=50","report.workload.summary.polling.max-attempts=10"})
public class WorkloadSummaryReportRoutes_DirectAggregateVMWorkloadInventoryModelTest_ThrowException {
    @Inject
    CamelContext camelContext;

    @EndpointInject(uri = "mock:direct:calculate-workloadsummaryreportmodel")
    private MockEndpoint mockCalculateVMWorkloadInventoryModel;

    @Autowired
    WorkloadInventoryReportRepository workloadInventoryReportRepository;

    @Autowired
    AnalysisRepository analysisRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Long analysisId;

    @Before
    public void setup()
    {
        AnalysisModel analysisModel = new AnalysisModel();
        analysisModel = analysisRepository.save(analysisModel);
        analysisId = analysisModel.getId();
    }

    @Test
    public void DirectAggregateVMWorkloadInventoryModel_ShouldThrowException() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        expectedException.expectCause(isA(CamelExecutionException.class));
        expectedException.expectMessage("Unable to find the expected");

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

        //Then
        Object response = responseFuture.join();
        camelContext.stop();
    }

}
