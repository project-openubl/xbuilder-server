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
        AnalysisModel analysisModel = service.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        assertThat(analysisModel).isNotNull();
        assertThat(analysisModel.getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.IN_PROGRESS.toString());
        assertThat(analysisModel.getOwner()).isEqualToIgnoringCase("user name");
        service.updateStatus(AnalysisService.STATUS.FAILED.toString(), analysisModel.getId());
        analysisModel = service.findByOwnerAndId("user name", analysisModel.getId());
        assertThat(analysisModel.getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.FAILED.toString());
    }

    @Test
    public void analysisService_NewAnalysisGiven_ShouldFilterByOwner() {
        AnalysisModel analysisModel = service.buildAndSave("reportName", "reportDescription", "payloadName", "user name");

        analysisModel = service.findByOwnerAndId("user name", analysisModel.getId());
        assertThat(analysisModel).isNotNull();

        analysisModel = service.findByOwnerAndId("whatever", analysisModel.getId());
        assertThat(analysisModel).isNull();
    }

    @Test
    public void analysisService_NewAnalysisGiven_ShouldCountByOwner() {
        service.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        service.buildAndSave("reportName", "reportDescription", "payloadName", "mrizzi@redhat.com");
        service.buildAndSave("reportName", "reportDescription", "payloadName", "mrizzi@redhat.com");

        assertThat(service.countByOwner("user name")).isEqualTo(1);
        assertThat(service.countByOwner("mrizzi@redhat.com")).isEqualTo(2);
        assertThat(service.countByOwner("whatever")).isEqualTo(0);
    }

    @Test
    public void analysisService_FailedAnalysisGiven_ShouldMarkAsFailedWhenNotInCreatedStatus() {
        AnalysisModel analysisModel = service.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        service.markAsFailedIfNotCreated(analysisModel.getId());
        assertThat(service.findByOwnerAndId("user name", analysisModel.getId()).getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.FAILED.toString());
    }

    @Test
    public void analysisService_FailedAnalysisGiven_ShouldNOTMarkAsFailedWhenInCreatedStatus() {
        AnalysisModel analysisModel = service.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        service.updateStatus(AnalysisService.STATUS.CREATED.toString(), analysisModel.getId());
        service.markAsFailedIfNotCreated(analysisModel.getId());
        assertThat(service.findByOwnerAndId("user name", analysisModel.getId()).getStatus()).isEqualToIgnoringCase(AnalysisService.STATUS.CREATED.toString());
    }

    @Test
    public void analysisService_ConcreteStatusGiven_ShouldReturnOnlyIfStutusIsNotEqualAsProvided() {
        AnalysisModel analysisModel = service.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        service.updateStatus(AnalysisService.STATUS.IN_PROGRESS.toString(), analysisModel.getId());
        assertThat(service.findByIdAndStatusIgnoreCaseNot(analysisModel.getId(), AnalysisService.STATUS.IN_PROGRESS.toString())).isNull();
        assertThat(service.findByIdAndStatusIgnoreCaseNot(analysisModel.getId(), AnalysisService.STATUS.FAILED.toString())).isNotNull();
    }
}
