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
package io.github.project.openubl.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.project.openubl.xmlbuilder.utils.CertificateDetails;
import io.github.project.openubl.xmlbuilder.utils.CertificateDetailsFactory;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog1;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog19;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog22;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog6;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ClienteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ProveedorInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.DocumentLineInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.creditNote.CreditNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.debitNote.DebitNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.keycloak.common.util.PemUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class ServerInfoResourceTest {

    @Test
    public void testConfigProperties() throws JsonProcessingException {
        given()
                .when()
                .get(ApiApplication.API_BASE + "/server-info")
                .then()
                .statusCode(200)
                .body("applicationConfig.igv", is(0.18f),
                        "applicationConfig.ivap", is(0.04f),
                        "applicationConfig.icb", is(0.20f),
                        "applicationConfig.defaultMoneda", is("PEN"),
                        "applicationConfig.defaultUnidadMedida", is("NIU"),
                        "applicationConfig.defaultTipoIgv", is("GRAVADO_OPERACION_ONEROSA"),
                        "applicationConfig.defaultTipoNotaCredito", is("ANULACION_DE_LA_OPERACION"),
                        "applicationConfig.defaultTipoNotaDebito", is("AUMENTO_EN_EL_VALOR"),
                        "applicationConfig.defaultRegimenPercepcion", is("VENTA_INTERNA"),
                        "applicationConfig.defaultRegimenRetencion", is("TASA_TRES"),
                        "applicationConfig.timeZone", is("America/Lima"),
                        "applicationConfig.serverKeystoreLocation", is(nullValue()),
                        "applicationConfig.serverKeystorePassword", is(nullValue())
                );
    }

    @Test
    public void testSystemClock() {
        given()
                .when()
                .get(ApiApplication.API_BASE + "/server-info")
                .then()
                .statusCode(200)
                .body("applicationConfig.timeZone", is("America/Lima"),
                        "clock.time", is(notNullValue()),
                        "clock.timeZone", is("America/Lima")
                );
    }

}
