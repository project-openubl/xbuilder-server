package org.openublpe.xmlbuilder.signer.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class OrganizationsResourceTest {

    @Test
    public void testGetOrganizations() throws Exception {
        // GIVEN

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/organizations")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
    }
}
