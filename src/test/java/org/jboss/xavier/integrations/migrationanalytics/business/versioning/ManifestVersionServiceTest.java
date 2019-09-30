package org.jboss.xavier.integrations.migrationanalytics.business.versioning;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.jboss.xavier.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
@UseAdviceWith // Disables automatic start of Camel context
@ActiveProfiles("test")
public class ManifestVersionServiceTest {
    @Inject
    ManifestVersionService manifestVersionServiceBean;

    @Test
    public void expandVersion_fewShortAndLargeVersionsGiven_ReturnsFullVersionTexts() {
        ManifestVersionService manifestVersionService = new ManifestVersionService();
        assertThat(manifestVersionService.expandVersion("1")).isEqualToIgnoringCase("1_0_0");
        assertThat(manifestVersionService.expandVersion("1_0")).isEqualToIgnoringCase("1_0_0");
        assertThat(manifestVersionService.expandVersion("1_0_0")).isEqualToIgnoringCase("1_0_0");
    }

    @Test
    public void getFallbackVersion_FewShortAndLongVersions_ReturnsClosestHighestVersions() {
        ManifestVersionService manifestVersionService = new ManifestVersionService();
        Map<String,String> properties = new HashMap<>();
        properties.put("cloudforms.manifest.1_0.primero", "val1");
        properties.put("cloudforms.manifest.1_1.primero", "val2");
        properties.put("cloudforms.manifest.1_1_2.primero","val3");
        properties.put("cloudforms.manifest.2.primero","val4");
        properties.put("cloudforms.manifest.1_2.primero","val5");
        properties.put("cloudforms.manifest.2_2.primero","val6");
        properties.put("cloudforms.manifest.2_1_3.primero","val7");
        properties.put("cloudforms.manifest.3_1_3.segundo","val8");
        manifestVersionService.setProperties(properties);
        assertThat(manifestVersionService.getFallbackVersionPath("1_0_0", "primero")).isEqualToIgnoringCase("1_0_0");
        assertThat(manifestVersionService.getFallbackVersionPath("1_3", "primero")).isEqualToIgnoringCase("1_2_0");
    }

    @Test
    public void getFallbackVersion_PropertiesFileGive_ReturnClosesHighestVersionPaths() {
        assertThat(manifestVersionServiceBean.getPropertyWithFallbackVersion("10_3", "vmworkloadinventory.providerPath")).isEqualToIgnoringCase("providerPath_v10_2_3");
        assertThat(manifestVersionServiceBean.getPropertyWithFallbackVersion("0_2_0", "hypervisor.cpuTotalCoresPath")).isEqualToIgnoringCase("cpu_total_cores");
        assertThat(manifestVersionServiceBean.getPropertyWithFallbackVersion("20_0_80", "vmworkloadinventory.providerPath")).isEqualToIgnoringCase("providerPath_v20");
        assertThat(manifestVersionServiceBean.getPropertyWithFallbackVersion("30_0_80", "vmworkloadinventory.providerPath")).isEqualToIgnoringCase("providerPath_v20_1_2");
        assertThat(manifestVersionServiceBean.getPropertyWithFallbackVersion("30", "vmworkloadinventory.providerPath")).isEqualToIgnoringCase("providerPath_v20_1_2");
    }




}
