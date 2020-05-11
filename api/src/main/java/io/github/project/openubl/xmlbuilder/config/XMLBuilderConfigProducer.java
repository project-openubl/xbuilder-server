package io.github.project.openubl.xmlbuilder.config;

import io.github.project.openubl.xmlbuilderlib.config.XMLBuilderConfig;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog10;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog7;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog9;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
public class XMLBuilderConfigProducer implements XMLBuilderConfig {

    @ConfigProperty(name = "openubl.igv")
    BigDecimal igv;

    @ConfigProperty(name = "openubl.ivap")
    BigDecimal ivap;

    @ConfigProperty(name = "openubl.icb")
    BigDecimal icb;

    @ConfigProperty(name = "openubl.defaultMoneda")
    String defaultMoneda;

    @ConfigProperty(name = "openubl.defaultUnidadMedida")
    String defaultUnidadMedida;

    @ConfigProperty(name = "openubl.defaultTipoIgv")
    String defaultTipoIgv;

    @ConfigProperty(name = "openubl.defaultTipoNotaCredito")
    String defaultTipoNotaCredito;

    @ConfigProperty(name = "openubl.defaultTipoNotaDebito")
    String defaultTipoNotaDebito;

    @Override
    public BigDecimal getIgv() {
        return igv;
    }

    @Override
    public BigDecimal getIvap() {
        return ivap;
    }

    @Override
    public BigDecimal getDefaultIcb() {
        return icb;
    }

    @Override
    public String getDefaultMoneda() {
        return defaultMoneda;
    }

    @Override
    public String getDefaultUnidadMedida() {
        return defaultUnidadMedida;
    }

    @Override
    public Catalog7 getDefaultTipoIgv() {
        return Catalog.valueOfCode(Catalog7.class, defaultTipoIgv)
                .orElseThrow(Catalog.invalidCatalogValue);
    }

    @Override
    public Catalog9 getDefaultTipoNotaCredito() {
        return Catalog.valueOfCode(Catalog9.class, defaultTipoNotaCredito)
                .orElseThrow(Catalog.invalidCatalogValue);
    }

    @Override
    public Catalog10 getDefaultTipoNotaDebito() {
        return Catalog.valueOfCode(Catalog10.class, defaultTipoNotaDebito)
                .orElseThrow(Catalog.invalidCatalogValue);
    }

}
