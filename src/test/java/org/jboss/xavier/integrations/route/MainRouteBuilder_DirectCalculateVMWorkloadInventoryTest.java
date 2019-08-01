package org.jboss.xavier.integrations.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.integrations.migrationanalytics.business.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jms:queue:vm-workload-inventory")
@UseAdviceWith // Disables automatic start of Camel context
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class MainRouteBuilder_DirectCalculateVMWorkloadInventoryTest {
    @Inject
    CamelContext camelContext;

    @Inject
    MainRouteBuilder mainRouteBuilder;

    @EndpointInject(uri = "mock:jms:queue:vm-workload-inventory")
    private MockEndpoint mockJmsQueue;

    @Test
    public void mainRouteBuilder_DirectCalculate_JSONGiven_ShouldReturnExpectedCalculatedValues() throws Exception {
        //Given
        camelContext.setTracing(true);
        camelContext.setAutoStartup(false);

        String customerId = "CID123";
        String fileName = "cloudforms-export-v1.json";
        Integer sourceproductindicator = null;
        Double year1hypervisorpercentage = 10D;
        Double year2hypervisorpercentage = 20D;
        Double year3hypervisorpercentage = 30D;
        Double growthratepercentage = 7D;

        VMWorkloadInventoryModel expectedVmWorkloadInventoryModel = new VMWorkloadInventoryModel();
        expectedVmWorkloadInventoryModel.setCluster("V2V_Cluster");
        expectedVmWorkloadInventoryModel.setCpuCores(1);
        expectedVmWorkloadInventoryModel.setDatacenter("V2V-DC");
        expectedVmWorkloadInventoryModel.setDiskSpace(1355808768L);
        expectedVmWorkloadInventoryModel.setGuestOSFullName("Red Hat Enterprise Linux Server release 7.4 (Maipo)");
        expectedVmWorkloadInventoryModel.setHasRdmDisk(false);
        expectedVmWorkloadInventoryModel.setMemory(2147483648L);
        expectedVmWorkloadInventoryModel.setNicsCount(1);
        expectedVmWorkloadInventoryModel.setOsProductName("Linux");
        expectedVmWorkloadInventoryModel.setProvider("VMware");
        expectedVmWorkloadInventoryModel.setVmName("james-db-03-copy");
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("org_id", customerId);
        metadata.put(Calculator.YEAR_1_HYPERVISORPERCENTAGE, year1hypervisorpercentage);
        metadata.put(Calculator.YEAR_2_HYPERVISORPERCENTAGE, year2hypervisorpercentage);
        metadata.put(Calculator.YEAR_3_HYPERVISORPERCENTAGE, year3hypervisorpercentage);
        metadata.put(Calculator.GROWTHRATEPERCENTAGE, growthratepercentage);

        Map<String, Object> headers = new HashMap<>();
        headers.put("MA_metadata", metadata);

        //When
        camelContext.start();
        camelContext.startRoute("calculate-vmworkloadinventory");
        String body = IOUtils.resourceToString(fileName, StandardCharsets.UTF_8, this.getClass().getClassLoader());
        
        camelContext.createProducerTemplate().sendBodyAndHeaders("direct:calculate-vmworkloadinventory", body, headers);

        Thread.sleep(5000);
        
        //Then
        assertThat(mockJmsQueue.getExchanges().get(0).getIn().getBody(VMWorkloadInventoryModel.class)).isEqualToComparingFieldByFieldRecursively(expectedVmWorkloadInventoryModel);
        assertThat(mockJmsQueue.getExchanges().size()).isEqualTo(21);

        camelContext.stop();
    }

}