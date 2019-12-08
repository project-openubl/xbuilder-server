package org.openublpe.xmlbuilder.signer.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.signer.representations.idm.OrganizationRepresentation;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class OrganizationsResourceTest {

    @Test
    public void testGetMasterOrganizations() throws Exception {
        // GIVEN

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/organizations")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        List<OrganizationRepresentation> output = new ObjectMapper().readValue(responseBody.asInputStream(), List.class);
        assertEquals(1, output.size());
    }
}
