package org.openublpe.xmlbuilder.signer.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class RemoteDocumentResourceTest {



    @Test
    public void testCreateInvoice() throws Exception {
        // GIVEN
        InvoiceInputModel input = InputGenerator.buildInvoiceInputModel();
        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/documents/invoice/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
    }
}
