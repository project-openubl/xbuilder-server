package org.openubl.xmlbuilder.test.ubl.summarydocument;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openubl.xmlbuilder.test.AbstractUBLTest;
import org.openubl.xmlbuilder.test.UBLDocumentType;
import org.openubl.xmlbuilder.test.XMlBuilderOutputResponse;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteAfectadoInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteValorVentaInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentImpuestosInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.SummaryDocumentComprobanteAfectadoOutputModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

@QuarkusTest
public class SummaryDocumentTest extends AbstractUBLTest {

    @Test
    void testSummaryDocument_adicionarBoleta() throws Exception {
        // Given
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_adicionarBoleta.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testSummaryDocument_adicionarNotaCreditoDeBoleta() throws Exception {
        // Given
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
                                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                                        .withSerieNumero("BC01-1")
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
                                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                                        .withTipo(Catalog1.BOLETA.toString())
                                        .withSerieNumero("B001-1")
                                        .build()
                                )
                                .build()
                ))
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_adicionarNotaCreditoDeBoleta.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testSummaryDocument_anularBoleta() throws Exception {
        // Given
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
                                .withTipoOperacion(Catalog19.ANULADO.toString())
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_anularBoleta.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testSummaryDocument_anularNotaDeCreditoDeBoleta() throws Exception {
        // Given
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
                                .withTipoOperacion(Catalog19.ANULADO.toString())
                                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                                        .withSerieNumero("BC01-1")
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
                                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                                        .withTipo(Catalog1.BOLETA.toString())
                                        .withSerieNumero("B001-1")
                                        .build()
                                )
                                .build()
                ))
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_anularNotaCreditoDeBoleta.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testSummaryDocument_modificarBoleta() throws Exception {
        // Given
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
                                .withTipoOperacion(Catalog19.MODIFICAR.toString())
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_modificarBoleta.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testSummaryDocument_modificarNotaDeCreditoDeBoleta() throws Exception {
        // Given
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
                                .withTipoOperacion(Catalog19.MODIFICAR.toString())
                                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                                        .withSerieNumero("BC01-1")
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
                                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                                        .withTipo(Catalog1.BOLETA.toString())
                                        .withSerieNumero("B001-1")
                                        .build()
                                )
                                .build()
                ))
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_modificarNotaCreditoDeBoleta.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

    @Test
    void testSummaryDocument_adicionarBoletaConICB() throws Exception {
        // Given
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
                                                .withIcb(new BigDecimal("2"))
                                                .build()
                                        )
                                        .withValorVenta(SummaryDocumentComprobanteValorVentaInputModel.Builder.aSummaryDocumentComprobanteValorVentaInputModel()
                                                .withImporteTotal(new BigDecimal("120"))
                                                .withGravado(new BigDecimal("100"))
                                                .build()
                                        )
                                        .build()
                                )
                                .build()
                ))
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.SUMMARY_DOCUMENT, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/summarydocument/summaryDocument_adicionarBoletaConIcb.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.SUMMARY_DOCUMENT, response.getApiSignerCreateResponse());
    }

}
