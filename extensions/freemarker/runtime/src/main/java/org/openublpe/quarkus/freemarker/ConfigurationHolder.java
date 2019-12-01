package org.openublpe.quarkus.freemarker;

import freemarker.template.Configuration;

public class ConfigurationHolder {

    private static Configuration configuration;

    static void initialize() {
        ConfigurationHolder.configuration = new Configuration(Configuration.VERSION_2_3_28);
        ConfigurationHolder.configuration.setClassForTemplateLoading(FreemarkerConfig.class, "/");
    }

    static Configuration getConfiguration() {
        return configuration;
    }
}
