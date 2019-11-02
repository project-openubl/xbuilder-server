package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.ubl21.UBL21Reader;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.input.ClienteInputModel;
import org.openublpe.xmlbuilder.models.input.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.ProveedorInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog6;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class DocumentsResourceTest {

    @Test
    public void testHelloEndpoint() throws JsonProcessingException {
        InvoiceInputModel invoice = buildInvoice();

        String body = new ObjectMapper().writeValueAsString(invoice);

        Response response = given()
                .body(body)
                    .header("Content-Type", "application/json")
                .when()
                    .post("/documents/invoice/create")
                .thenReturn();

        assertEquals(200, response.getStatusCode());

//        InvoiceType read = UBL21Reader.invoice().read(response.getBody().asInputStream());
//        assertNotNull(read);
    }

    InvoiceInputModel buildInvoice () {
        InvoiceInputModel invoice = new InvoiceInputModel();
        invoice.setSerie("F5");
        invoice.setNumero(123);
        invoice.setFechaEmision(new Date().getTime());

        ProveedorInputModel proveedor = new ProveedorInputModel();
        invoice.setProveedor(proveedor);
        proveedor.setRuc("12345678912");
        proveedor.setCodigoPostal("050101");
        proveedor.setRazonSocial("Wolsnut4 S.A.C.");

        ClienteInputModel cliente = new ClienteInputModel();
        invoice.setCliente(cliente);
        cliente.setNombre("Carlos Feria");
        cliente.setNumeroDocumentoIdentidad("12345678");
        cliente.setTipoDocumentoIdentidad(Catalog6.DNI.toString());

        List<DetalleInputModel> detalle = new ArrayList<>();
        invoice.setDetalle(detalle);

        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item1");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(BigDecimal.TEN);

        DetalleInputModel item2 = new DetalleInputModel();
        detalle.add(item2);
        item2.setDescripcion("item2");
        item2.setCantidad(BigDecimal.TEN);
        item2.setPrecioUnitario(BigDecimal.ONE);

        return invoice;
    }

}