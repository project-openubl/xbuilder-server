package org.openublpe.quarkus.freemarker;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.List;

@ConfigRoot(name = "xml-builder.freemarker", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class FreemarkerBuildConfig {
    /**
     * Comma-separated list of locations to scan recursively for templates. The location type is determined by its prefix.
     * Unprefixed locations or locations starting with classpath: point to a package on the classpath and may FTL templates.
     * Locations starting with filesystem: point to a directory on the filesystem, may only contain FTL templates and are only
     * scanned recursively down non-hidden directories.
     */
    @ConfigItem
    public List<String> locations;
}
