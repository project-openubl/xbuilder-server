package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.input.ClienteInputModel;
import org.openublpe.xmlbuilder.models.input.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.ProveedorInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog;
import org.openublpe.xmlbuilder.models.ubl.Catalog6;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InvoiceResourceTest {

    @Test
    public void testHelloEndpoint() throws JsonProcessingException {
        InvoiceInputModel invoice = new InvoiceInputModel();
        invoice.setSerie("F5");
        invoice.setNumero(123);
        invoice.setFechaEmision(new Date().getTime());
        invoice.setMoneda("PEN");

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

        String body = new ObjectMapper().writeValueAsString(invoice);

        given()
                .body(body)
                .header("Content-Type", "application/json")
        .when()
                .post("/invoices/build-xml")
        .then()
             .statusCode(200);
//             .body(is("hello"));
    }

}