package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.input.InvoiceInputModel;

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