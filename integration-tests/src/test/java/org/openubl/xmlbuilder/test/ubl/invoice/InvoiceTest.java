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
package org.openubl.xmlbuilder.test.ubl.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openubl.xmlbuilder.test.AbstractUBLTest;
import org.openubl.xmlbuilder.test.UBLDocumentType;
import org.openubl.xmlbuilder.test.XMlBuilderOutputResponse;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ContactoInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

@QuarkusTest
public class InvoiceTest extends AbstractUBLTest {

    @Test
    void testInvoiceWithCustomUnidadMedida() throws Exception {
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
                                .withPrecioUnitario(new BigDecimal(100))
                                .withUnidadMedida("KGM")
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .withUnidadMedida("KGM")
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/customUnidadMedida.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceWithCustomFechaEmision() throws Exception {
        // Given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 6, 0, 0, 0);

        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
                .withFechaEmision(calendar.getTimeInMillis())
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/customFechaEmision.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceWithCustomClienteDireccionAndContacto() throws Exception {
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
                        .withContacto(ContactoInputModel.Builder.aContactoInputModel()
                                .withEmail("carlos@gmail.com")
                                .withTelefono("+123456789")
                                .build())
                        .withDireccion(DireccionInputModel.Builder.aDireccionInputModel()
                                .withUbigeo("050101")
                                .withDepartamento("Ayacucho")
                                .withProvincia("Huamanga")
                                .withDistrito("Jesus Nazareno")
                                .withCodigoLocal("010101")
                                .withUrbanizacion("000000")
                                .withDireccion("Jr. Las piedras 123")
                                .withCodigoPais("PE")
                                .build())
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/customClienteDireccionAndContacto.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceWithCustomProveedorDireccionAndContacto() throws Exception {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .withContacto(ContactoInputModel.Builder.aContactoInputModel()
                                .withEmail("carlos@gmail.com")
                                .withTelefono("+123456789")
                                .build())
                        .withDireccion(DireccionInputModel.Builder.aDireccionInputModel()
                                .withUbigeo("050101")
                                .withDepartamento("Ayacucho")
                                .withProvincia("Huamanga")
                                .withDistrito("Jesus Nazareno")
                                .withCodigoLocal("010101")
                                .withUrbanizacion("000000")
                                .withDireccion("Jr. Las piedras 123")
                                .withCodigoPais("PE")
                                .build())
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/customProveedorDireccionAndContacto.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceWithCustomFirmante() throws Exception {
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
                .withFirmante(FirmanteInputModel.Builder.aFirmanteInputModel()
                        .withRuc("000000000000")
                        .withRazonSocial("Wolsnut4 S.A.C.")
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

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/customFirmante.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceWithICB_precioUnitario() throws Exception {
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
                .withFirmante(FirmanteInputModel.Builder.aFirmanteInputModel()
                        .withRuc("000000000000")
                        .withRazonSocial("Wolsnut4 S.A.C.")
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .withIcb(true)
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .withIcb(true)
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/icb.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }

    @Test
    void testInvoiceWithICB_precioConIgv() throws Exception {
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
                .withFirmante(FirmanteInputModel.Builder.aFirmanteInputModel()
                        .withRuc("000000000000")
                        .withRazonSocial("Wolsnut4 S.A.C.")
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConIgv(new BigDecimal(118))
                                .withIcb(true)
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioConIgv(new BigDecimal(118))
                                .withIcb(true)
                                .build())
                )
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.INVOICE, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/invoice/icb.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
        assertSendSunat(UBLDocumentType.INVOICE, response.getApiSignerCreateResponse());
    }
}
