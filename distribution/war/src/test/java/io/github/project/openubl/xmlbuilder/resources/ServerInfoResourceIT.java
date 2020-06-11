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

import io.github.project.openubl.xmlbuilder.resources.ApiApplication;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.net.URL;

import static io.restassured.RestAssured.given;

@RunAsClient
@RunWith(Arquillian.class)
public class ServerInfoResourceIT extends AbstractResourceTest {

    @Test
    public void testConfigProperties() {
        given()
                .when()
                .get(deploymentURL.toString() + ApiApplication.API_BASE2 + "/server-info")
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
                .get(deploymentURL.toString() + ApiApplication.API_BASE2 + "/server-info")
                .then()
                .statusCode(200)
                .body("applicationConfig.timeZone", is("America/Lima"),
                        "clock.time", is(notNullValue()),
                        "clock.timeZone", is("America/Lima")
                );
    }

}
