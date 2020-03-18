package org.openubl.xmlbuilder.test.ubl.invoice.tipoigv;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openubl.xmlbuilder.test.AbstractUBLTest;
import org.openubl.xmlbuilder.test.UBLDocumentType;
import org.openubl.xmlbuilder.test.XMlBuilderOutputResponse;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.Arrays;

@QuarkusTest
public class InvoiceTipoIgvTest extends AbstractUBLTest {

    @Test
    void testInvoiceTipoIgv_GravadoOnerosa_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_OPERACION_ONEROSA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_OPERACION_ONEROSA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoOnerosa.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoOnerosa_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_OPERACION_ONEROSA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_OPERACION_ONEROSA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoOnerosa.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorPremio_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorPremio.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorPremio_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorPremio.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorDonacion_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_DONACION.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_DONACION.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorDonacion.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorDonacion_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_DONACION.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_DONACION.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorDonacion.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiro_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiro.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiro_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiro.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorPublicidad_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PUBLICIDAD.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PUBLICIDAD.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorPublicidad.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorPublicidad_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PUBLICIDAD.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_PUBLICIDAD.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorPublicidad.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoBonificaciones_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_BONIFICACIONES.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_BONIFICACIONES.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoBonificaciones.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoBonificaciones_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_BONIFICACIONES.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_BONIFICACIONES.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoBonificaciones.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorEntregaATrabajadores_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorEntregaATrabajadores.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoRetiroPorEntregaATrabajadores_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(118))
                                .withTipoIgv(Catalog7.GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoRetiroPorEntregaATrabajadores.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoIVAP_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.GRAVADO_IVAP.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.GRAVADO_IVAP.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoIVAP.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_GravadoIVAP_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(104))
                                .withTipoIgv(Catalog7.GRAVADO_IVAP.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(104))
                                .withTipoIgv(Catalog7.GRAVADO_IVAP.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/gravadoIVAP.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_ExoneradoOperacionOnerosa_precionSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.EXONERADO_OPERACION_ONEROSA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXONERADO_OPERACION_ONEROSA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/exoneradoOperacionOnerosa.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_ExoneradoOperacionOnerosa_precionConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXONERADO_OPERACION_ONEROSA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXONERADO_OPERACION_ONEROSA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/exoneradoOperacionOnerosa.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_ExoneradoTransferenciaGratuita_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.EXONERADO_TRANSFERENCIA_GRATUITA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXONERADO_TRANSFERENCIA_GRATUITA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/exoneradoTransferenciaGratuita.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_ExoneradoTransferenciaGratuita_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXONERADO_TRANSFERENCIA_GRATUITA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXONERADO_TRANSFERENCIA_GRATUITA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/exoneradoTransferenciaGratuita.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoOperacionOnerosa_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_OPERACION_ONEROSA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_OPERACION_ONEROSA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoOperacionOnerosa.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoOperacionOnerosa_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_OPERACION_ONEROSA.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_OPERACION_ONEROSA.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoOperacionOnerosa.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorBonificacion_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorBonificacion.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorBonificacion_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorBonificacion.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiro_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiro.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiro_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiro.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorMuestrasMedicas_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoPorMuestrasMedicas.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorMuestrasMedicas_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoPorMuestrasMedicas.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorConvenioColectivo_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorConvenioColectivo.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorConvenioColectivo_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorConvenioColectivo.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorPremio_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PREMIO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PREMIO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorPremio.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorPremio_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PREMIO.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PREMIO.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorPremio.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorPublicidad_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PUBLICIDAD.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PUBLICIDAD.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorPublicidad.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_InafectoRetiroPorPublicidad_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PUBLICIDAD.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.INAFECTO_RETIRO_POR_PUBLICIDAD.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/inafectoRetiroPorPublicidad.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_Exportacion_precioSinImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withTipoIgv(Catalog7.EXPORTACION.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioSinImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXPORTACION.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/exportacion.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceTipoIgv_Exportacion_precioConImpuestos() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
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
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXPORTACION.toString())
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConImpuestos(new BigDecimal(100))
                                .withTipoIgv(Catalog7.EXPORTACION.toString())
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/tipoigv/exportacion.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }
}
