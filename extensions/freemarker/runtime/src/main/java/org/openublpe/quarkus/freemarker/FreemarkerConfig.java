package org.openublpe.quarkus.freemarker;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.List;

@ConfigRoot(name = "xmlbuilder.freemarker", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class FreemarkerConfig {
    /**
     * A comma separated list of templates to compile.
     */
    @ConfigItem
    public List<String> sources;
}
