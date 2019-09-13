package org.jboss.xavier.integrations.jpa.service;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.jboss.xavier.analytics.pojo.output.workload.inventory.WorkloadInventoryReportModel;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.jboss.xavier.integrations.route.model.WorkloadInventoryFilterBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class WorkloadInventoryReportServiceTest {

    @Inject
    private AnalysisService analysisService;

    @Inject
    private WorkloadInventoryReportService reportService;

    @Test
    public void workloadInventoryReportService_NewReportGiven_ShouldPersistEntityAndFilterByAnalysisOwnerAndFilterBean() {
        AnalysisModel analysisModel = analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        WorkloadInventoryReportModel reportModel0 = new WorkloadInventoryReportModel();
        reportModel0.setVmName("host-0");
        reportModel0.setCreationDate(new Date());
        WorkloadInventoryReportModel reportModel1 = new WorkloadInventoryReportModel();
        reportModel1.setVmName("host-1");
        reportModel1.setCreationDate(new Date());
        List<WorkloadInventoryReportModel> reportModels = new ArrayList<>(2);
        reportModels.add(reportModel0);
        reportModels.add(reportModel1);
        analysisService.addWorkloadInventoryReportModels(reportModels, analysisModel.getId());

        PageBean pageBean = new PageBean(0, 5);
        SortBean sortBean = new SortBean("id", false);
        WorkloadInventoryFilterBean filterBean = new WorkloadInventoryFilterBean();
        filterBean.setVmNames(Collections.singleton("host"));

        Page<WorkloadInventoryReportModel> result = reportService.findByAnalysisOwnerAndAnalysisId("user name", analysisModel.getId(), pageBean, sortBean, filterBean);
        assertThat(result.getContent().size()).isEqualTo(2);

        result = reportService.findByAnalysisOwnerAndAnalysisId("whatever", analysisModel.getId(), pageBean, sortBean, filterBean);
        assertThat(result.getContent().size()).isEqualTo(0);
    }

    @Test
    public void workloadInventoryReportService_NewReportGiven_ShouldPersistEntityAndFilterByAnalysisOwner() {
        AnalysisModel analysisModel = analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        WorkloadInventoryReportModel reportModel0 = new WorkloadInventoryReportModel();
        reportModel0.setVmName("host-0");
        reportModel0.setCreationDate(new Date());
        WorkloadInventoryReportModel reportModel1 = new WorkloadInventoryReportModel();
        reportModel1.setVmName("host-1");
        reportModel1.setCreationDate(new Date());
        List<WorkloadInventoryReportModel> reportModels = new ArrayList<>(2);
        reportModels.add(reportModel0);
        reportModels.add(reportModel1);
        analysisService.addWorkloadInventoryReportModels(reportModels, analysisModel.getId());

        List<WorkloadInventoryReportModel> result = reportService.findByAnalysisOwnerAndAnalysisId("user name", analysisModel.getId());
        assertThat(result.size()).isEqualTo(2);

        result = reportService.findByAnalysisOwnerAndAnalysisId("whatever", analysisModel.getId());
        assertThat(result.size()).isEqualTo(0);
    }
}
