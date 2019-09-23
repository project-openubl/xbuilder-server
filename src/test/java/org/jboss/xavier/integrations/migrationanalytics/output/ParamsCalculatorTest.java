package org.jboss.xavier.integrations.migrationanalytics.output;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.integrations.migrationanalytics.business.Calculator;
import org.jboss.xavier.integrations.migrationanalytics.business.ParamsCalculator;
import org.jboss.xavier.integrations.route.MainRouteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class ParamsCalculatorTest {
    @SpyBean
    private ParamsCalculator reportCalculator;

    @Test
    public void analyticsCalculator_calculate_CloudFormsModelGiven_ShouldReturn2HostAndTotalDiskSpace() throws IOException {
        // Given
        String filename = "cloudforms-export-v1.json";
        String customerid = "CIDE9988";

        String cloudFormJSON = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(filename), "UTF-8");
        Integer hypervisor = 2;
        Long totaldiskspace = 563902124032L;
        Integer sourceproductindicator = null;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;
        Long analysisId = 3L;

        UploadFormInputDataModel expectedFormInputDataModel = new UploadFormInputDataModel(customerid, filename, hypervisor,
                totaldiskspace, sourceproductindicator,
                year1hypervisorpercentage/100, year2hypervisorpercentage/100,
                year3hypervisorpercentage/100, growthratepercentage/100, analysisId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("filename", filename);
        headers.put("org_id", customerid);
        headers.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, year1hypervisorpercentage);
        headers.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, year2hypervisorpercentage);
        headers.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, year3hypervisorpercentage);
        headers.put(Calculator.GROWTHRATEPERCENTAGE, growthratepercentage);
        headers.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());

        // When
        UploadFormInputDataModel inputDataModelCalculated = reportCalculator.calculate(cloudFormJSON, headers);

        // Then
        assertThat(inputDataModelCalculated).isEqualToComparingFieldByFieldRecursively(expectedFormInputDataModel);
    }

    @Test
    public void analyticsCalculator_calculate_CloudFormsModelWithNotIntDivisionGiven_ShouldReturn2HostAndTotalDiskSpace() throws IOException {
        // Given
        String filename = "cloudforms-export-v1.json";
        String customerid = "CIDE9988";

        String cloudFormJSON = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(filename), "UTF-8");
        cloudFormJSON = cloudFormJSON.replace("\"cpu_cores_per_socket\": 8", "\"cpu_cores_per_socket\": 16");
        Integer hypervisor = 2;
        Long totaldiskspace = 563902124032L;
        Integer sourceproductindicator = null;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;
        Long analysisId = 3L;

        UploadFormInputDataModel expectedFormInputDataModel = new UploadFormInputDataModel(customerid, filename, hypervisor,
                totaldiskspace, sourceproductindicator,
                year1hypervisorpercentage/100, year2hypervisorpercentage/100,
                year3hypervisorpercentage/100, growthratepercentage/100, analysisId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("filename", filename);
        headers.put("org_id", customerid);
        headers.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, year1hypervisorpercentage);
        headers.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, year2hypervisorpercentage);
        headers.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, year3hypervisorpercentage);
        headers.put(Calculator.GROWTHRATEPERCENTAGE, growthratepercentage);
        headers.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());

        // When
        UploadFormInputDataModel inputDataModelCalculated = reportCalculator.calculate(cloudFormJSON, headers);

        // Then
        assertThat(inputDataModelCalculated).isEqualToComparingFieldByFieldRecursively(expectedFormInputDataModel);
    }

    @Test
    public void analyticsCalculator_calculate_CloudFormsModelWithNotExistingVersionGiven_ShouldFallbackToV1AndReturn2HostAndTotalDiskSpace() throws IOException {
        // Given
        doReturn("v2").when(reportCalculator).getManifestVersion(any());
        analyticsCalculator_calculate_CloudFormsModelGiven_ShouldReturn2HostAndTotalDiskSpace();
    }
}
