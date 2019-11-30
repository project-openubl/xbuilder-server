package org.openublpe.xmlbuilder;

import freemarker.template.Configuration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FreemarkerConfiguration {

    private Configuration configuration;

    @PostConstruct
    void init() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(FreemarkerConfiguration.class, "/");
    }

    @Produces
    public Configuration getConfiguration() {
        return configuration;
    }

}
