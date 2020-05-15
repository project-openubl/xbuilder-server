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
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DocumentsResourceTest {

    @Test
    void testInvoice() throws JsonProcessingException {
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
                .post(ApiApplication.API_BASE + "/documents/invoice/enrich")
                .then()
                .statusCode(200);
        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/invoice/create")
                .then()
                .statusCode(200);
    }

    @Test
    void testCreditNote() throws JsonProcessingException {
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("FC01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("F001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
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
                .post(ApiApplication.API_BASE + "/documents/credit-note/enrich")
                .then()
                .statusCode(200);
        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/credit-note/create")
                .then()
                .statusCode(200);
    }

    @Test
    void testDebitNote() throws JsonProcessingException {
        DebitNoteInputModel input = DebitNoteInputModel.Builder.aDebitNoteInputModel()
                .withSerie("FD01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("F001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
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
                .post(ApiApplication.API_BASE + "/documents/debit-note/enrich")
                .then()
                .statusCode(200);
        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/debit-note/create")
                .then()
                .statusCode(200);
    }

    @Test
    void testVoidedDocument() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 1, 20, 30, 59);

        VoidedDocumentInputModel input = VoidedDocumentInputModel.Builder.aVoidedDocumentInputModel()
                .withNumero(1)
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withDescripcionSustento("mi razon de baja")
                .withComprobante(VoidedDocumentLineInputModel.Builder.aVoidedDocumentLineInputModel()
                        .withSerieNumero("F001-1")
                        .withTipoComprobante(Catalog1.FACTURA.toString())
                        .withFechaEmision(calendar.getTimeInMillis())
                        .build()
                )
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(input);

        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/voided-document/enrich")
                .then()
                .statusCode(200);
        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/voided-document/create")
                .then()
                .statusCode(200);
    }

    @Test
    void testSummaryDocument() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 1, 20, 30, 59);

        SummaryDocumentInputModel input = SummaryDocumentInputModel.Builder.aSummaryDocumentInputModel()
                .withNumero(1)
                .withFechaEmisionDeComprobantesAsociados(calendar.getTimeInMillis())
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withDetalle(Collections.singletonList(
                        SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                                .withTipoOperacion(Catalog19.ADICIONAR.toString())
                                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                                        .withTipo(Catalog1.BOLETA.toString())
                                        .withSerieNumero("B001-1")
                                        .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                                                .withNombre("Carlos Feria")
                                                .withNumeroDocumentoIdentidad("12345678")
                                                .withTipoDocumentoIdentidad(Catalog6.DNI.toString())
                                                .build()
                                        )
                                        .withImpuestos(SummaryDocumentImpuestosInputModel.Builder.aSummaryDocumentImpuestosInputModel()
                                                .withIgv(new BigDecimal("100"))
                                                .build()
                                        )
                                        .withValorVenta(SummaryDocumentComprobanteValorVentaInputModel.Builder.aSummaryDocumentComprobanteValorVentaInputModel()
                                                .withImporteTotal(new BigDecimal("118"))
                                                .withGravado(new BigDecimal("100"))
                                                .build()
                                        )
                                        .build()
                                )
                                .build()
                ))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(input);

        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/summary-document/enrich")
                .then()
                .statusCode(200);
        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/summary-document/create")
                .then()
                .statusCode(200);
    }

    @Disabled("Disabled until perception is implemented")
    @Test
    void testPerception() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 1, 20, 30, 59);

        PerceptionInputModel input = PerceptionInputModel.Builder.aPerceptionInputModel()
                .withSerie("P001")
                .withNumero(1)
                .withRegimen(Catalog22.VENTA_INTERNA.toString())
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12121212121")
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        PerceptionRetentionLineInputModel.Builder.aPerceptionRetentionLineInputModel()
                                .withComprobante(PerceptionRetentionComprobanteInputModel.Builder.aPerceptionRetentionComprobanteInputModel()
                                        .withMoneda("PEN")
                                        .withTipo(Catalog1.FACTURA.toString())
                                        .withSerieNumero("F001-1")
                                        .withFechaEmision(calendar.getTimeInMillis())
                                        .withImporteTotal(new BigDecimal("100"))
                                        .build()
                                )
                                .build(),
                        PerceptionRetentionLineInputModel.Builder.aPerceptionRetentionLineInputModel()
                                .withComprobante(PerceptionRetentionComprobanteInputModel.Builder.aPerceptionRetentionComprobanteInputModel()
                                        .withMoneda("PEN")
                                        .withTipo(Catalog1.FACTURA.toString())
                                        .withSerieNumero("F001-1")
                                        .withFechaEmision(calendar.getTimeInMillis())
                                        .withImporteTotal(new BigDecimal("100"))
                                        .build()
                                )
                                .build()
                ))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(input);

        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/perception/enrich")
                .then()
                .statusCode(200);
        given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/perception/create")
                .then()
                .statusCode(200);
    }

    @Disabled("Disabled until retention is implemented")
    @Test
    void testRetention() {
    }

}
