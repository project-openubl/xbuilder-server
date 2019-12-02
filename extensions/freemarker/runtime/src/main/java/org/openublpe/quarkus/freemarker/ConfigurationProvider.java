package org.openublpe.quarkus.freemarker;

import freemarker.template.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ConfigurationProvider {

    @Produces
    public Configuration configuration() {
        return ConfigurationHolder.getConfiguration();
    }

}
