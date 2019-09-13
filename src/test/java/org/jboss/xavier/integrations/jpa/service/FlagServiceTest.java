package org.jboss.xavier.integrations.jpa.service;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.FlagModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadModel;
import org.jboss.xavier.analytics.pojo.output.workload.summary.WorkloadSummaryReportModel;
import org.jboss.xavier.integrations.route.model.PageBean;
import org.jboss.xavier.integrations.route.model.SortBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class FlagServiceTest {

    @Inject
    private AnalysisService analysisService;

    @Inject
    private FlagService reportService;

    @Test
    public void flagService_NewReportGiven_ShouldPersistEntityAndFilterByAnalysisOwner() {
        AnalysisModel analysisModel = analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        WorkloadSummaryReportModel reportModel = new WorkloadSummaryReportModel();
        FlagModel flagModel = new FlagModel();
        flagModel.setFlag("RDM");
        flagModel.setOsName("Fedora");
        flagModel.setClusters(3);
        flagModel.setVms(11);
        reportModel.setFlagModels(Arrays.asList(flagModel));
        analysisService.setWorkloadSummaryReportModel(reportModel, analysisModel.getId());

        PageBean pageBean = new PageBean(0, 5);
        SortBean sortBean = new SortBean("id", false);

        Page<FlagModel> result = reportService.findByReportAnalysisOwnerAndReportAnalysisId("user name", analysisModel.getId(), pageBean, sortBean);
        assertThat(result.getContent().size()).isEqualTo(1);

        result = reportService.findByReportAnalysisOwnerAndReportAnalysisId("whatever", analysisModel.getId(), pageBean, sortBean);
        assertThat(result.getContent().size()).isEqualTo(0);
    }
}
