package org.jboss.xavier.integrations.migrationanalytics.business;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.IOUtils;
import org.jboss.xavier.Application;
import org.jboss.xavier.analytics.pojo.input.workload.inventory.VMWorkloadInventoryModel;
import org.jboss.xavier.integrations.route.MainRouteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class VMWorkloadInventoryCalculatorTest {
    @Inject
    VMWorkloadInventoryCalculator calculator;

    @Test
    public void calculate_jsonGiven_ShouldReturnCalculatedValues() throws IOException {
        String cloudFormsJson = IOUtils.resourceToString("cloudforms-export-v1.json", StandardCharsets.UTF_8, VMWorkloadInventoryCalculatorTest.class.getClassLoader());
        Map<String, Object> headers = new HashMap<>();
        Long analysisId = 30L;
        headers.put(MainRouteBuilder.ANALYSIS_ID, analysisId.toString());

        Collection<VMWorkloadInventoryModel> modelList = calculator.calculate(cloudFormsJson, headers);
        assertThat(Integer.valueOf(modelList.size())).isEqualTo(21);
        assertThat(modelList.stream().filter(e -> e.getNicsCount() == 2).count()).isEqualTo(4);
        assertThat(modelList.stream().filter(e -> e.getVmName().equalsIgnoreCase("james-db-03-copy")).count()).isEqualTo(2);
        assertThat(modelList.stream().filter(e -> e.getVmName().equalsIgnoreCase("dev-windows-server-2008-TEST")).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getGuestOSFullName().equalsIgnoreCase("Microsoft Windows Server 2008 R2 (64-bit)")).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getDiskSpace() == (17179869184L + 10737418240L)).count()).isEqualTo(2);

        VMWorkloadInventoryModel expectedModel = new VMWorkloadInventoryModel();
        expectedModel.setVmName("dev-windows-server-2008-TEST");
        expectedModel.setProvider("VMware");
        expectedModel.setOsProductName("ServerNT");
        expectedModel.setNicsCount(1);
        expectedModel.setMemory(4294967296L);
        expectedModel.setHasRdmDisk(false);
        expectedModel.setGuestOSFullName("Microsoft Windows Server 2008 R2 (64-bit)");
        expectedModel.setDiskSpace(7437787136L);
        expectedModel.setDatacenter("V2V-DC");
        expectedModel.setCpuCores(1);
        expectedModel.setCluster("V2V_Cluster");
        expectedModel.setSystemServicesNames(Arrays.asList("{02B0078E-2148-45DD-B7D3-7E37AAB3B31D}","xmlprov","wudfsvc"));
        expectedModel.setVmDiskFilenames(Arrays.asList("[NFS_Datastore] dev-windows-server-2008/dev-windows-server-2008.vmdk"));
        expectedModel.setAnalysisId(analysisId);
        expectedModel.setHost_name("esx13.v2v.bos.redhat.com");
        expectedModel.setVersion("6.5");
        expectedModel.setProduct("VMware vCenter");
        HashMap<String, String> files = new HashMap<>();
        files.put("/root/.bash_profile","# .bash_profile\n\n# Get the aliases and functions\nif [ -f ~/.bashrc ]; then\n\t. ~/.bashrc\nfi\n\n# User specific environment and startup programs\n\nPATH=$PATH:$HOME/bin\nexport PATH\nexport JAVA_HOME=/usr/java/jdk1.5.0_07/bin/java\nexport WAS_HOME=/opt/IBM/WebSphere/AppServer\n");
        files.put("/opt/IBM", null);
        expectedModel.setFiles(files);

        assertThat(modelList.stream().filter(e -> e.getVmName().equalsIgnoreCase("dev-windows-server-2008-TEST")).findFirst().get()).isEqualToComparingFieldByField(expectedModel);
    }

    @Test
    public void calculate_jsonV1_0_0_Given_ShouldReturnCalculatedValues() throws IOException {
        String cloudFormsJson = IOUtils.resourceToString("cloudforms-export-v1_0_0.json", StandardCharsets.UTF_8, VMWorkloadInventoryCalculatorTest.class.getClassLoader());
        Map<String, Object> headers = new HashMap<>();
        Long analysisId = 30L;
        headers.put(MainRouteBuilder.ANALYSIS_ID, analysisId);

        Collection<VMWorkloadInventoryModel> modelList = calculator.calculate(cloudFormsJson, headers);
        assertThat(Integer.valueOf(modelList.size())).isEqualTo(8);
        assertThat(modelList.stream().filter(e -> e.getNicsCount() == 2).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getVmName().equalsIgnoreCase("hana")).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getVmName().equalsIgnoreCase("tomcat")).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getOsProductName().equalsIgnoreCase("Linux")).count()).isEqualTo(7);
        assertThat(modelList.stream().filter(e -> e.getOsProductName().equalsIgnoreCase("CentOS 7 (64-bit)")).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getGuestOSFullName().equalsIgnoreCase("CentOS 7 (64-bit)")).count()).isEqualTo(1);
        assertThat(modelList.stream().filter(e -> e.getGuestOSFullName().equalsIgnoreCase("Red Hat Enterprise Linux Server release 7.6 (Maipo)")).count()).isEqualTo(6);
        assertThat(modelList.stream().filter(e -> e.getDiskSpace() == (17980588032L)).count()).isEqualTo(1);

        VMWorkloadInventoryModel expectedModel = new VMWorkloadInventoryModel();
        expectedModel.setVmName("oracle_db");
        expectedModel.setProvider("vSphere");
        expectedModel.setOsProductName("Linux");
        expectedModel.setNicsCount(1);
        expectedModel.setMemory(8589934592L);
        expectedModel.setHasRdmDisk(false);
        expectedModel.setGuestOSFullName("CentOS Linux release 7.6.1810 (Core) ");
        expectedModel.setDiskSpace(17980588032L);
        expectedModel.setDatacenter("JON TEST DC");
        expectedModel.setCpuCores(2);
        expectedModel.setCluster("VMCluster");
        expectedModel.setSystemServicesNames(Arrays.asList("NetworkManager-dispatcher","NetworkManager-wait-online","NetworkManager"));
        expectedModel.setVmDiskFilenames(Arrays.asList("[NFS-Storage] oracle_db_1/", "[NFS-Storage] oracle_db_1/oracle_db.vmdk", "[NFS-Storage] oracle_db_1/"));
        expectedModel.setAnalysisId(analysisId);
        expectedModel.setHost_name("host-47");
        expectedModel.setVersion("6.7.2");
        expectedModel.setProduct("VMware vCenter");
        HashMap<String, String> files = new HashMap<>();
        files.put("/etc/GeoIP.conf","dummy content");
        files.put("/etc/asound.conf", null);
        files.put("/etc/autofs.conf", null);
        expectedModel.setFiles(files);

        assertThat(modelList.stream().filter(e -> e.getVmName().equalsIgnoreCase("oracle_db")).findFirst().get()).isEqualToComparingFieldByField(expectedModel);
    }
}
