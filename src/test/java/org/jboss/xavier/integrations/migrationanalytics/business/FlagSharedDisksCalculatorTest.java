package org.jboss.xavier.integrations.migrationanalytics.business;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.integrations.route.MainRouteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class FlagSharedDisksCalculatorTest {
    @Inject
    FlagSharedDisksCalculator calculator;

    @Test
    public void calculate_jsonGiven_ShouldReturnCalculatedValues() throws IOException {
        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008-TEST");
        expectedVmNamesWithSharedDisk.add("james-db-03-copy");
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008");
        expectedVmNamesWithSharedDisk.add("pemcg-rdm-test");

        String cloudFormsJson = IOUtils.resourceToString("cloudforms-export-v1.json", StandardCharsets.UTF_8, FlagSharedDisksCalculatorTest.class.getClassLoader());
        Map<String, Object> headers = new HashMap<>();
        Long analysisId = 30L;
        headers.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());

        Set<String> vmNamesWithSharedDisk = calculator.calculate(cloudFormsJson, headers);
        assertThat(Integer.valueOf(vmNamesWithSharedDisk.size())).isEqualTo(4);
        assertThat(vmNamesWithSharedDisk).isEqualTo(expectedVmNamesWithSharedDisk);
    }

    @Test
    public void calculate_jsonV1_0_0_Given_ShouldReturnCalculatedValues() throws IOException {
        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("tomcat");
        expectedVmNamesWithSharedDisk.add("lb");

        String cloudFormsJson = IOUtils.resourceToString("cloudforms-export-v1_0_0.json", StandardCharsets.UTF_8, FlagSharedDisksCalculatorTest.class.getClassLoader());
        Map<String, Object> headers = new HashMap<>();
        Long analysisId = 30L;
        headers.put(MainRouteBuilder.ANALYSIS_ID, analysisId);

        Set<String> vmNamesWithSharedDisk = calculator.calculate(cloudFormsJson, headers);
        assertThat(Integer.valueOf(vmNamesWithSharedDisk.size())).isEqualTo(2);
        assertThat(vmNamesWithSharedDisk).isEqualTo(expectedVmNamesWithSharedDisk);
    }
}
