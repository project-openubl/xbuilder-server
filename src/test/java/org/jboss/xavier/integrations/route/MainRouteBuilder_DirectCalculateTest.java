package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.UploadFormInputDataModel;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;
import org.jboss.xavier.integrations.migrationanalytics.business.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jms:queue:uploadFormInputDataModel|direct:vm-workload-inventory|direct:calculate-workloadsummaryreportmodel")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectCalculateTest {
    @Inject
    CamelContext camelContext;

    @Inject
    AnalysisService analysisService;

    @EndpointInject(uri = "mock:jms:queue:uploadFormInputDataModel")
    private MockEndpoint mockJmsQueueCostSavings;

    @EndpointInject(uri = "mock:direct:vm-workload-inventory")
    private MockEndpoint mockDirectWorkloadInventory;

    @EndpointInject(uri = "mock:direct:calculate-workloadsummaryreportmodel")
    private MockEndpoint mockCalculateWorkloadSummaryReportModel;

    @Test
    public void mainRouteBuilder_DirectCalculate_PersistedNotificationGiven_ShouldCallFileWithGivenHeaders() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer hypervisor = 2;
        Long totaldiskspace = 563902124032L;
        Integer sourceproductindicator = null;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;
        Long analysisId = analysisModel.getId();

        UploadFormInputDataModel expectedFormInputDataModelExpected = new UploadFormInputDataModel(customerId, fileName, hypervisor, totaldiskspace,
                sourceproductindicator, year1hypervisorpercentage/100, year2hypervisorpercentage/100,
                year3hypervisorpercentage/100, growthratepercentage/100, analysisId);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, year1hypervisorpercentage);
        metadata.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, year2hypervisorpercentage);
        metadata.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, year3hypervisorpercentage);
        metadata.put(Calculator.GROWTHRATEPERCENTAGE, growthratepercentage);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        //When
        camelContext.start();
        camelContext.startRoute("calculate-costsavings");
        String body = IOUtils.resourceToString(fileName, StandardCharsets.UTF_8, MainRouteBuilder_DirectCalculateTest.class.getClassLoader());

        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:calculate-costsavings", body, headers);

        //Then
        assertThat(mockJmsQueueCostSavings.getExchanges().get(0).getIn().getBody()).isEqualToComparingFieldByFieldRecursively(expectedFormInputDataModelExpected);

        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectCalculate_FileGiven_ShouldSendMessageToJMS() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");

        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockJmsQueueCostSavings.expectedMessageCount(1);

        String fileName = "cloudforms-export-v1.json";

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("dummy", "dummy");
        metadata.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, 10D);
        metadata.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, 20D);
        metadata.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, 30D);
        metadata.put(Calculator.GROWTHRATEPERCENTAGE, 7D);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisModel.getId());

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);
        headers.put("Content-type", "application/zip");

        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008-TEST");
        expectedVmNamesWithSharedDisk.add("james-db-03-copy");
        expectedVmNamesWithSharedDisk.add("dev-windows-server-2008");
        expectedVmNamesWithSharedDisk.add("pemcg-rdm-test");

        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");
        camelContext.startRoute("calculate");
        camelContext.startRoute("calculate-costsavings");
        camelContext.startRoute("calculate-vmworkloadinventory");
        camelContext.startRoute("flags-shared-disks");
        String body = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"));

        camelContext.createProducerTemplate().request("direct:calculate", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        Thread.sleep(5000);
        //Then
        mockJmsQueueCostSavings.assertIsSatisfied();
        assertThat(mockJmsQueueCostSavings.getExchanges().get(0).getIn().getBody(UploadFormInputDataModel.class).getTotalDiskSpace()).isEqualTo(563902124032L);
        assertThat(mockJmsQueueCostSavings.getExchanges().get(0).getIn().getBody(UploadFormInputDataModel.class).getHypervisor()).isEqualTo(2);
        assertThat(mockDirectWorkloadInventory.getExchanges().get(0).getIn().getBody(VMWorkloadInventoryModel.class).getVmName()).isNotEmpty();
        Set<String> vmNamesWithSharedDisk = mockCalculateWorkloadSummaryReportModel.getExchanges().get(0).getIn().getBody(Set.class);
        assertThat(vmNamesWithSharedDisk.size()).isEqualTo(4);
        assertThat(vmNamesWithSharedDisk).isEqualTo(expectedVmNamesWithSharedDisk);
        camelContext.stop();
    }

    @Test
    public void mainRouteBuilder_DirectCalculateWithV1_0_0_FileGiven_ShouldSendMessageToJMS() throws Exception {
        //Given
        AnalysisModel analysisModel = analysisService.buildAndSave("report name", "report desc", "file name", "user name");
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);
        mockJmsQueueCostSavings.expectedMessageCount(1);

        String fileName = "cloudforms-export-v1_0_0.json";

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("dummy", "dummy");
        metadata.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, 10D);
        metadata.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, 20D);
        metadata.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, 30D);
        metadata.put(Calculator.GROWTHRATEPERCENTAGE, 7D);
        metadata.put(MainRouteBuilder.ANALYSIS_ID, analysisModel.getId());

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);
        headers.put("Content-type", "application/zip");

        Set<String> expectedVmNamesWithSharedDisk = new HashSet<>();
        expectedVmNamesWithSharedDisk.add("tomcat");
        expectedVmNamesWithSharedDisk.add("lb");

        //When
        camelContext.start();
        camelContext.startRoute("unzip-file");
        camelContext.startRoute("calculate");
        camelContext.startRoute("calculate-costsavings");
        camelContext.startRoute("calculate-vmworkloadinventory");
        camelContext.startRoute("flags-shared-disks");
        String body = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"));

        camelContext.createProducerTemplate().request("direct:calculate", exchange -> {
            exchange.getIn().setBody(getClass().getClassLoader().getResourceAsStream(fileName));
            exchange.getIn().setHeaders(headers);
        });

        Thread.sleep(5000);
        //Then
        mockJmsQueueCostSavings.assertIsSatisfied();
        assertThat(mockJmsQueueCostSavings.getExchanges().get(0).getIn().getBody(UploadFormInputDataModel.class).getTotalDiskSpace()).isEqualTo(146028888064L);
        assertThat(mockJmsQueueCostSavings.getExchanges().get(0).getIn().getBody(UploadFormInputDataModel.class).getHypervisor()).isEqualTo(4);
        assertThat(mockDirectWorkloadInventory.getExchanges().stream().noneMatch(exchange -> exchange.getIn().getBody(VMWorkloadInventoryModel.class).getVmName().isEmpty())).isTrue();
        assertThat(mockDirectWorkloadInventory.getExchanges().stream().filter(exchange -> exchange.getIn().getBody(VMWorkloadInventoryModel.class).getOsProductName().equals("CentOS 7 (64-bit)")).count()).isEqualTo(1);
        assertThat(mockDirectWorkloadInventory.getExchanges().stream().filter(exchange -> exchange.getIn().getBody(VMWorkloadInventoryModel.class).getOsProductName().equals("Linux")).count()).isEqualTo(7);
        Set<String> vmNamesWithSharedDisk = mockCalculateWorkloadSummaryReportModel.getExchanges().get(0).getIn().getBody(Set.class);
        assertThat(vmNamesWithSharedDisk.size()).isEqualTo(2);
        assertThat(vmNamesWithSharedDisk).isEqualTo(expectedVmNamesWithSharedDisk);
        camelContext.stop();
    }

}
