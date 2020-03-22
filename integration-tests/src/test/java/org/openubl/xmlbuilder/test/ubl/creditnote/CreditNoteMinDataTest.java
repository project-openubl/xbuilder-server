package org.openubl.xmlbuilder.test.ubl.creditnote;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openubl.xmlbuilder.test.AbstractUBLTest;
import org.openubl.xmlbuilder.test.UBLDocumentType;
import org.openubl.xmlbuilder.test.XMlBuilderOutputResponse;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;

import java.math.BigDecimal;
import java.util.Arrays;

@QuarkusTest
public class CreditNoteMinDataTest extends AbstractUBLTest {

    @Test
    void testCreditNoteWithMinDataSent_customerWithRuc() throws Exception {
        // Given
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
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_RUC.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_customerWithDni() throws Exception {
        // Given
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("B001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12345678")
                        .withTipoDocumentoIdentidad(Catalog6.DNI.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_DNI.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_customerWithDocTribNoDomSinRuc() throws Exception {
        // Given
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("B001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12345678")
                        .withTipoDocumentoIdentidad(Catalog6.DOC_TRIB_NO_DOM_SIN_RUC.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_DocTribNoDomSinRuc.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_customerWithExtranjeria() throws Exception {
        // Given
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("B001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12345678")
                        .withTipoDocumentoIdentidad(Catalog6.EXTRANJERIA.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_Extranjeria.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_customerWithPasaporte() throws Exception {
        // Given
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("B001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12345678")
                        .withTipoDocumentoIdentidad(Catalog6.PASAPORTE.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_Pasaporte.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_customerWithDecDiplomatica() throws Exception {
        // Given
        CreditNoteInputModel input = CreditNoteInputModel.Builder.aCreditNoteInputModel()
                .withSerie("BC01")
                .withNumero(1)
                .withSerieNumeroComprobanteAfectado("B001-1")
                .withDescripcionSustento("mi sustento")
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12345678")
                        .withTipoDocumentoIdentidad(Catalog6.DEC_DIPLOMATICA.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_DecDiplomatica.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_usePrecioSinImpuestos() throws Exception {
        // Given
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
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_UsePrecioConSinImpuestos.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_usePrecioConImpuestos() throws Exception {
        // Given
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
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_UsePrecioConSinImpuestos.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_usePrecioSinImpuestosAndCantidadThreeDecimals() throws Exception {
        // Given
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
                                .withCantidad(new BigDecimal("10.123"))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal("10.123"))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_UsePrecioConSinImpuestosAndCantidadThreeDecimals.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }

    @Test
    void testCreditNoteWithMinDataSent_usePrecioConImpuestosAndCantidadThreeDecimals() throws Exception {
        // Given
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
                                .withCantidad(new BigDecimal("10.123"))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal("10.123"))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.CREDIT_NOTE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/creditnote/mindata/MinData_UsePrecioConSinImpuestosAndCantidadThreeDecimals.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.CREDIT_NOTE, response.getApiSignerCreateResponse());
    }
}
