package org.openubl.xmlbuilder.test.ubl.voideddocument;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openubl.xmlbuilder.test.AbstractUBLTest;
import org.openubl.xmlbuilder.test.UBLDocumentType;
import org.openubl.xmlbuilder.test.XMlBuilderOutputResponse;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ContactoInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentLineInputModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

@QuarkusTest
public class VoidedDocumentTest extends AbstractUBLTest {

    @Test
    void testVoidedDocument_Factura_MinData() throws Exception {
        // Given
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.VOIDED_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/voideddocument/factura_minData.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.VOIDED_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testVoidedDocument_NotaCreditoDeFactura_MinData() throws Exception {
        // Given
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
                        .withSerieNumero("FC01-1")
                        .withTipoComprobante(Catalog1.NOTA_CREDITO.toString())
                        .withFechaEmision(calendar.getTimeInMillis())
                        .build()
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.VOIDED_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/voideddocument/notaCreditoDeFactura.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.VOIDED_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testVoidedDocument_NotaDebitoDeFactura_MinData() throws Exception {
        // Given
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
                        .withSerieNumero("FD01-1")
                        .withTipoComprobante(Catalog1.NOTA_DEBITO.toString())
                        .withFechaEmision(calendar.getTimeInMillis())
                        .build()
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.VOIDED_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/voideddocument/notaDebitoDeFactura.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.VOIDED_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testVoided_GuiaRemisionRemitente_MinData() throws Exception {
        // Given
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
                        .withSerieNumero("T001-1")
                        .withTipoComprobante(Catalog1.GUIA_REMISION_REMITENTE.toString())
                        .withFechaEmision(calendar.getTimeInMillis())
                        .build()
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.VOIDED_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/voideddocument/guiaRemisionRemitente_minData.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
//        assertSendSunat(UBLDocumentType.VOIDED_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testVoided_Percepcion_MinData() throws Exception {
        // Given
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
                        .withSerieNumero("P001-1")
                        .withTipoComprobante(Catalog1.PERCEPCION.toString())
                        .withFechaEmision(calendar.getTimeInMillis())
                        .build()
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.VOIDED_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/voideddocument/percepcion_minData.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.VOIDED_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testVoided_Retencion_MinData() throws Exception {
        // Given
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
                        .withSerieNumero("R001-1")
                        .withTipoComprobante(Catalog1.RETENCION.toString())
                        .withFechaEmision(calendar.getTimeInMillis())
                        .build()
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.VOIDED_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/voideddocument/retencion_minData.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.VOIDED_DOCUMENT, response.getApiSignerCreateResponse());
    }
}
