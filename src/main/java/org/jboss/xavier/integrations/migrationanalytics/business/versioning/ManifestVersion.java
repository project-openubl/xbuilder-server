package org.jboss.xavier.integrations.migrationanalytics.business.versioning;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class ManifestVersion implements Comparable<ManifestVersion> {
    @Getter
    private final String[] version;

    public ManifestVersion(String version) {
        this.version = version.split("_");
    }

    @Override
    public int compareTo(ManifestVersion versionB) {
        int major = Integer.valueOf(version[0]).compareTo(Integer.valueOf(versionB.getVersion()[0]));
        int minor = Integer.valueOf(version[1]).compareTo(Integer.valueOf(versionB.getVersion()[1]));
        int patch = Integer.valueOf(version[2]).compareTo(Integer.valueOf(versionB.getVersion()[2]));

        return (major != 0) ? major : (minor != 0) ? minor : patch;
    }

    public int compareTo(String versionB) {
        return compareTo(new ManifestVersion(versionB));
    }

    public String getFullVersion() {
        return String.join("_", version);
    }
}
