package org.openublpe.quarkus.freemarker;

import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class FreemarkerConfigurationRecorder {

    public void initializeConfiguration() {
        ConfigurationHolder.initialize();
    }

}
