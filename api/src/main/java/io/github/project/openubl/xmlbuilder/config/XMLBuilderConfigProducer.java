/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.xmlbuilder.config;

import io.github.project.openubl.xmlbuilder.config.qualifiers.CDIProvider;
import io.github.project.openubl.xmlbuilderlib.config.Config;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog10;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog7;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog9;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@CDIProvider
@ApplicationScoped
public class XMLBuilderConfigProducer implements Config {

    @Inject
    @ConfigProperty(name = "openubl.igv")
    BigDecimal igv;

    @Inject
    @ConfigProperty(name = "openubl.ivap")
    BigDecimal ivap;

    @Inject
    @ConfigProperty(name = "openubl.icb")
    BigDecimal icb;

    @Inject
    @ConfigProperty(name = "openubl.defaultMoneda")
    String defaultMoneda;

    @Inject
    @ConfigProperty(name = "openubl.defaultUnidadMedida")
    String defaultUnidadMedida;

    @Inject
    @ConfigProperty(name = "openubl.defaultTipoIgv")
    String defaultTipoIgv;

    @Inject
    @ConfigProperty(name = "openubl.defaultTipoNotaCredito")
    String defaultTipoNotaCredito;

    @Inject
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
