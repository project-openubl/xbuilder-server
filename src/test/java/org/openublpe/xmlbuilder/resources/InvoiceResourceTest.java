package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.input.InvoiceInputModel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InvoiceResourceTest {

    @Test
    public void testHelloEndpoint() throws JsonProcessingException {
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F5");
        input.setNumero(123);

        String body = new ObjectMapper().writeValueAsString(input);

        given()
                .body(body)
        .when()
                .post("/invoices/build-xml")
        .then()
             .statusCode(200);
//             .body(is("hello"));
    }

}