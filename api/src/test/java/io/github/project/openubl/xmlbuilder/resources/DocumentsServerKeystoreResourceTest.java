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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.project.openubl.xmlbuilder.config.ServerKeystore;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog6;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ClienteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ProveedorInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.DocumentLineInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static io.restassured.RestAssured.given;

@QuarkusTest
class DocumentsServerKeystoreResourceTest {

    final static String KEYSTORE = "LLAMA-PE-CERTIFICADO-DEMO-10467793549.pfx";
    final static String KEYSTORE_PASSWORD = "password";

    @Inject
    ServerKeystore serverKeystore;

    public static class MockedServerKeystore extends ServerKeystore {
        @Override
        public boolean hasKeys() {
            return true;
        }

        @Override
        public String getKeystoreLocation() {
            URI uri;
            try {
                uri = Thread.currentThread().getContextClassLoader().getResource(KEYSTORE).toURI();
            } catch (URISyntaxException e) {
                throw new IllegalStateException(e);
            }

            File keystoreFile = new File(uri);
            return keystoreFile.getAbsolutePath();
        }

        @Override
        public String getKeystorePassword() {
            return KEYSTORE_PASSWORD;
        }
    }

    @Test
    void testSignInvoice() throws Exception {
        QuarkusMock.installMockForInstance(new MockedServerKeystore(), serverKeystore);

        // input
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Project OpenUBL S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12121212121")
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .build())
                )
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(input);

        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/invoice/create")
                .then()
                .statusCode(200);
    }

}
