package org.jboss.xavier.integrations.route;

import org.apache.commons.io.IOUtils;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.jpa.service.WorkloadInventoryReportService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainRouteBuilder_DirectCalculateFlagSharedDisksTest extends XavierCamelTest {
    @Inject
    AnalysisService analysisService;

    @MockBean
    private WorkloadInventoryReportService workloadInventoryReportService;

    @Test
    public void mainRouteBuilder_DirectCalculate_JSONGiven_ShouldReturnExpectedCalculatedValues() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008-TEST");
        expectedVmNamesWithSharedDisk.add("james-db-03-copy");
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008");
        expectedVmNamesWithSharedDisk.add("pemcg-rdm-test");
        List<WorkloadInventoryReportModel> workloadInventoryReportModels = new ArrayList<>(expectedVmNamesWithSharedDisk.size());
        expectedVmNamesWithSharedDisk.forEach(vm -> {
            WorkloadInventoryReportModel workloadInventoryReportModel = new WorkloadInventoryReportModel();
            workloadInventoryReportModel.setVmName(vm);
            workloadInventoryReportModels.add(workloadInventoryReportModel);
        });
        when(workloadInventoryReportService.findByAnalysisOwnerAndAnalysisId("user name", analysisModel.getId())).thenReturn(workloadInventoryReportModels);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Long analysisId = analysisModel.getId();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(RouteBuilderExceptionHandler.ANALYSIS_ID, analysisId.toString());

        Map<String, Object> headers = new HashMap<>();
        headers.put(RouteBuilderExceptionHandler.MA_METADATA, metadata);
        headers.put(RouteBuilderExceptionHandler.USERNAME, "user name");

        //When
        camelContext.start();
        camelContext.startRoute("flags-shared-disks");
        String body = IOUtils.resourceToString(fileName, StandardCharsets.UTF_8, this.getClass().getClassLoader());

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:flags-shared-disks", body, headers);

        verify(workloadInventoryReportService).saveAll(workloadInventoryReportModels);

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectCalculate_JSONOnVersion1_0_0Given_ShouldReturnExpectedCalculatedValues() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("tomcat");
        expectedVmNamesWithSharedDisk.add("lb");
        List<WorkloadInventoryReportModel> workloadInventoryReportModels = new ArrayList<>(expectedVmNamesWithSharedDisk.size());
        expectedVmNamesWithSharedDisk.forEach(vm -> {
            WorkloadInventoryReportModel workloadInventoryReportModel = new WorkloadInventoryReportModel();
            workloadInventoryReportModel.setVmName(vm);
            workloadInventoryReportModels.add(workloadInventoryReportModel);
        });
        when(workloadInventoryReportService.findByAnalysisOwnerAndAnalysisId("user name", analysisModel.getId())).thenReturn(workloadInventoryReportModels);


        String customerId = "CID123";
        String fileName = "cloudforms-export-v1_0_0.json";
        Long analysisId = analysisModel.getId();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(RouteBuilderExceptionHandler.ANALYSIS_ID, analysisId.toString());

        Map<String, Object> headers = new HashMap<>();
        headers.put(RouteBuilderExceptionHandler.MA_METADATA, metadata);
        headers.put(RouteBuilderExceptionHandler.USERNAME, "user name");

        //When
        camelContext.start();
        camelContext.startRoute("flags-shared-disks");
        String body = IOUtils.resourceToString(fileName, StandardCharsets.UTF_8, this.getClass().getClassLoader());

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:flags-shared-disks", body, headers);

        //Then
        verify(workloadInventoryReportService).saveAll(workloadInventoryReportModels);

        camelContext.stop();
    }

}
