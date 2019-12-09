package org.openublpe.xmlbuilder.signer.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.KeysMetadataRepresentation;
import org.openublpe.xmlbuilder.signer.models.OrganizationType;
import org.openublpe.xmlbuilder.signer.representations.idm.ComponentRepresentation;
import org.openublpe.xmlbuilder.signer.representations.idm.OrganizationRepresentation;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
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
        ResponseBody responseBody = response.getBody();

        List<OrganizationRepresentation> output = new ObjectMapper().readValue(responseBody.asInputStream(), List.class);
        assertEquals(1, output.size());
    }

    @Test
    public void testGetOrganization() throws Exception {
        // GIVEN

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/organizations/master")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
        assertNotNull(output);
        assertEquals("master", output.getId());
    }

    @Test
    public void testCreateOrganization() throws Exception {
        // GIVEN
        OrganizationRepresentation organization = new OrganizationRepresentation();
        organization.setName("myCompanyNamae");
        organization.setDescription("myCompanyDescription");
        organization.setUseCustomCertificates(false);
        organization.setType("master"); // this field should be used, check asserts

        String body = new ObjectMapper().writeValueAsString(organization);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/organizations")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
        assertEquals(organization.getName(), output.getName());
        assertEquals(organization.getDescription(), output.getDescription());
        assertEquals(organization.getUseCustomCertificates(), output.getUseCustomCertificates());
        assertEquals(OrganizationType.common.toString(), output.getType());
    }

    @Test
    public void testUpdateOrganization() throws Exception {
        // GIVEN
        String organizationId = "master";

        OrganizationRepresentation organization = new OrganizationRepresentation();
        organization.setName("myNewMasterName");
        organization.setDescription("myNewMasterDescription");
        organization.setUseCustomCertificates(false);
        organization.setType("common"); // this field should never change, check asserts

        String body = new ObjectMapper().writeValueAsString(organization);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .put("/organizations/" + organizationId)
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
        assertEquals(organization.getName(), output.getName());
        assertEquals(organization.getDescription(), output.getDescription());
        assertEquals(organization.getUseCustomCertificates(), output.getUseCustomCertificates());
        assertEquals(OrganizationType.master.toString(), output.getType());
    }

    @Test
    public void testGetKeyMetadata() throws Exception {
        // GIVEN
        String organizationId = "master";

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/organizations/" + organizationId + "/keys")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        KeysMetadataRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), KeysMetadataRepresentation.class);
        assertNotNull(output);
        assertFalse(output.getActive().isEmpty());
        assertFalse(output.getKeys().isEmpty());
    }

    @Test
    public void testGetComponents() throws Exception {
        // GIVEN
        String organizationId = "master";

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/organizations/" + organizationId + "/components")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        List<ComponentRepresentation> output = new ObjectMapper().readValue(responseBody.asInputStream(), List.class);
        assertNotNull(output);
        assertFalse(output.isEmpty());
    }
}
