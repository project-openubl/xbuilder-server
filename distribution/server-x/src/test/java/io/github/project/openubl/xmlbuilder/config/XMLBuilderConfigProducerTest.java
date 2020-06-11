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
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog10;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog7;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog9;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class XMLBuilderConfigProducerTest {

    @Inject
    @CDIProvider
    Config config;

    @Test
    void test_getIgv() {
        assertEquals(new BigDecimal("0.18"), config.getIgv());
    }

    @Test
    void getIvap() {
        assertEquals(new BigDecimal("0.04"), config.getIvap());
    }

    @Test
    void getDefaultIcb() {
        assertEquals(0, new BigDecimal("0.2").compareTo(config.getDefaultIcb()));
    }

    @Test
    void getDefaultMoneda() {
        assertEquals("PEN", config.getDefaultMoneda());
    }

    @Test
    void getDefaultUnidadMedida() {
        assertEquals("NIU", config.getDefaultUnidadMedida());
    }

    @Test
    void getDefaultTipoIgv() {
        assertEquals(Catalog7.GRAVADO_OPERACION_ONEROSA, config.getDefaultTipoIgv());
    }

    @Test
    void getDefaultTipoNotaCredito() {
        assertEquals(Catalog9.ANULACION_DE_LA_OPERACION, config.getDefaultTipoNotaCredito());
    }

    @Test
    void getDefaultTipoNotaDebito() {
        assertEquals(Catalog10.AUMENTO_EN_EL_VALOR, config.getDefaultTipoNotaDebito());
    }
}
