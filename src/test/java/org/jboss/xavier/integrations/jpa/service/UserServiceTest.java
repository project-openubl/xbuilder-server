package org.jboss.xavier.integrations.jpa.service;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class UserServiceTest {

    @Inject
    private AnalysisService analysisService;

    @Inject
    private UserService userService;

    @Test
    public void userService_AnalysisGiven_ShouldReturnUser() {
        analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "user name");
        analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "mrizzi@redhat.com");
        analysisService.buildAndSave("reportName", "reportDescription", "payloadName", "mrizzi@redhat.com");

        assertThat(userService.findUser("user name").isFirstTimeCreatingReports()).isEqualTo(false);
        assertThat(userService.findUser("mrizzi@redhat.com").isFirstTimeCreatingReports()).isEqualTo(false);
        assertThat(userService.findUser("whatever").isFirstTimeCreatingReports()).isEqualTo(true);
    }

    @Test
    public void userService_AuthorizedUsersEmpty_ShouldReturnNotAllowed() {
        ReflectionTestUtils.setField(userService, "authorizedAdminUsers", new String[0]);
        assertThat(userService.isUserAllowedToAdministratorResources("myUsername")).isEqualTo(false);
    }
    
    @Test
    public void userService_AuthorizedUsersGiven_ShouldReturnAllowedOrNot() {
        assertThat(userService.isUserAllowedToAdministratorResources("admin1")).isEqualTo(true);
        assertThat(userService.isUserAllowedToAdministratorResources("admin1@redhat.com")).isEqualTo(false);
        assertThat(userService.isUserAllowedToAdministratorResources("admin2@redhat.com")).isEqualTo(true);
    }
}
