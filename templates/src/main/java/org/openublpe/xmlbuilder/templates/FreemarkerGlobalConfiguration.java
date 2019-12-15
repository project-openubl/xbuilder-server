package org.openublpe.xmlbuilder.templates;

import freemarker.template.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FreemarkerGlobalConfiguration {

    @Inject
    Configuration configuration;

    @PostConstruct
    void init() {
        configuration.setClassForTemplateLoading(FreemarkerGlobalConfiguration.class, "/");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
