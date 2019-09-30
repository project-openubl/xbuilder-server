package org.jboss.xavier.integrations.migrationanalytics.business.versioning;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ManifestVersionTest {
    @Test
    public void compareVersion_FewVersionNumbersGiven_ReturnsCorrectComparisson() {
        assertThat(new ManifestVersion("1_0_0").compareTo("1_0_0")).isEqualTo(0);
        assertThat(new ManifestVersion("2_0_0").compareTo( "1_0_0")).isEqualTo(1);
        assertThat(new ManifestVersion("1_0_0").compareTo( "2_0_0")).isEqualTo(-1);
        assertThat(new ManifestVersion("1_1_0").compareTo( "2_0_0")).isEqualTo(-1);
        assertThat(new ManifestVersion("1_1_0").compareTo( "1_2_0")).isEqualTo(-1);
        assertThat(new ManifestVersion("1_2_0").compareTo( "1_1_0")).isEqualTo(1);
        assertThat(new ManifestVersion("1_2_1").compareTo( "1_1_0")).isEqualTo(1);
        assertThat(new ManifestVersion("1_2_1").compareTo( "1_2_2")).isEqualTo(-1);
        assertThat(new ManifestVersion("1_2_1").compareTo( "2_1_2")).isEqualTo(-1);
    }

    @Test
    public void sortVersions_FewVersionNumbersGiven_ReturnsCorrectOrder() {
        List<String> mylist = Arrays.asList("2_1_1","1_0_0", "1_8_2","1_1_0", "2_2_0");
        List<ManifestVersion> mySortedList = mylist.stream().map(ManifestVersion::new).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        assertThat(mySortedList.get(mySortedList.size() -1)).isEqualTo(new ManifestVersion("1_0_0"));
        assertThat(mySortedList.get(0)).isEqualTo(new ManifestVersion("2_2_0"));
    }
}
