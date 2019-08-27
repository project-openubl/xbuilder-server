package org.jboss.xavier.integrations.jpa.service;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class AnalysisServiceTest {

    @Inject
    private AnalysisService service;

    @Test
    public void analysisService_NewStatusGiven_ShouldChangeAndPersistTheEntity() {
      AnalysisModel analysisModel = service.buildAndSave("reportName", "reportDescription", "payloadName");
      assertThat(analysisModel).isNotNull();
      assertThat(analysisModel.getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.IN_PROGRESS.toString());
      service.updateStatus(AnalysisService.STATUS.FAILED.toString(), analysisModel.getId());
      analysisModel = service.findById(analysisModel.getId());
      assertThat(analysisModel.getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.FAILED.toString());
    }
}
