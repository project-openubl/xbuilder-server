package org.jboss.xavier.integrations.jpa.service;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.analytics.pojo.output.InitialSavingsEstimationReportModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class InitialSavingsEstimationReportServiceTest {

    @Inject
    private AnalysisService analysisService;

    @Inject
    private InitialSavingsEstimationReportService reportService;

    @Test
    public void initialSavingsEstimationReportService_NewReportGiven_ShouldPersistEntityAndFilterByAnalysisOwner() {
        AnalysisModel analysisModel = analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        InitialSavingsEstimationReportModel reportModel = new InitialSavingsEstimationReportModel();
        reportModel.setCreationDate(new Date());
        analysisService.setInitialSavingsEstimationReportModel(reportModel, analysisModel.getId());

        reportModel = reportService.findByAnalysisOwnerAndAnalysisId("user name", analysisModel.getId());
        assertThat(reportModel).isNotNull();

        reportModel = reportService.findByAnalysisOwnerAndAnalysisId("whatever", analysisModel.getId());
        assertThat(reportModel).isNull();
    }
}
