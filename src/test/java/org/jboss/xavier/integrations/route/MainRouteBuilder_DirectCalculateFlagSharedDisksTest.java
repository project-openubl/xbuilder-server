package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:calculate-workloadsummaryreportmodel")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectCalculateFlagSharedDisksTest {
    @Inject
    CamelContext camelContext;

    @Inject
    AnalysisService analysisService;

    @EndpointInject(uri = "mock:direct:calculate-workloadsummaryreportmodel")
    private MockEndpoint mockWorkloadSummaryReportModel;

    @Test
    public void mainRouteBuilder_DirectCalculate_JSONGiven_ShouldReturnExpectedCalculatedValues() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name");
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Long analysisId = analysisModel.getId();

        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008-TEST");
        expectedVmNamesWithSharedDisk.add("james-db-03-copy");
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008");
        expectedVmNamesWithSharedDisk.add("pemcg-rdm-test");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        //When
        camelContext.start();
        camelContext.startRoute("flags-shared-disks");
        String body = IOUtils.resourceToString(fileName, StandardCharsets.UTF_8, this.getClass().getClassLoader());

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:flags-shared-disks", body, headers);

        Thread.sleep(5000);

        //Then
        Set<String> vmNamesWithSharedDisk = mockWorkloadSummaryReportModel.getExchanges().get(0).getIn().getBody(Set.class);
        assertThat(vmNamesWithSharedDisk.size()).isEqualTo(4);
        assertThat(vmNamesWithSharedDisk).isEqualTo(expectedVmNamesWithSharedDisk);

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectCalculate_JSONOnVersion1_0_0Given_ShouldReturnExpectedCalculatedValues() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name");
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1_0_0.json";
        Long analysisId = analysisModel.getId();

        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("tomcat");
        expectedVmNamesWithSharedDisk.add("lb");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        //When
        camelContext.start();
        camelContext.startRoute("flags-shared-disks");
        String body = IOUtils.resourceToString(fileName, StandardCharsets.UTF_8, this.getClass().getClassLoader());

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:flags-shared-disks", body, headers);

        Thread.sleep(5000);

        //Then
        Set<String> vmNamesWithSharedDisk = mockWorkloadSummaryReportModel.getExchanges().get(0).getIn().getBody(Set.class);
        assertThat(vmNamesWithSharedDisk.size()).isEqualTo(2);
        assertThat(vmNamesWithSharedDisk).isEqualTo(expectedVmNamesWithSharedDisk);

        camelContext.stop();
    }

}
