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
package org.openubl.xmlbuilder.test.organization;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.KeysMetadataRepresentation;
import org.openublpe.xmlbuilder.apicore.resources.ApiApplication;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;
import org.openublpe.xmlbuilder.apisigner.representations.idm.ComponentRepresentation;
import org.openublpe.xmlbuilder.apisigner.representations.idm.OrganizationRepresentation;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class OrganizationsResourceTest {

    static final String ORGANIZATIONS_URL = ApiApplication.API_BASE + "/organizations";

    @Test
    void testGetOrganizations() throws Exception {
        // GIVEN

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(ORGANIZATIONS_URL)
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        List<OrganizationRepresentation> output = new ObjectMapper().readValue(responseBody.asInputStream(), List.class);
        assertEquals(1, output.size());
    }

    @Test
    void testGetOrganization() throws Exception {
        // GIVEN

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(ORGANIZATIONS_URL + "/master")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
        assertNotNull(output);
        assertEquals("master", output.getId());
    }

    @Test
    void testCreateOrganization() throws Exception {
        // GIVEN
        OrganizationRepresentation organization = new OrganizationRepresentation();
        organization.setName("myCompanyNamae");
        organization.setDescription("myCompanyDescription");
        organization.setUseMasterKeys(false);
        organization.setType("master"); // this field should be used, check asserts

        String body = new ObjectMapper().writeValueAsString(organization);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ORGANIZATIONS_URL)
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
        assertEquals(organization.getName(), output.getName());
        assertEquals(organization.getDescription(), output.getDescription());
        assertEquals(organization.getUseMasterKeys(), output.getUseMasterKeys());
        assertEquals(OrganizationType.common.toString(), output.getType());
    }

    @Test
    void testUpdateOrganization() throws Exception {
        // GIVEN
        String organizationId = "master";

        OrganizationRepresentation organization = new OrganizationRepresentation();
        organization.setName("myNewMasterName");
        organization.setDescription("myNewMasterDescription");
        organization.setUseMasterKeys(false);
        organization.setType("common"); // this field should never change, check asserts

        String body = new ObjectMapper().writeValueAsString(organization);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .put(ORGANIZATIONS_URL + "/" + organizationId)
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
        assertEquals(organization.getName(), output.getName());
        assertEquals(organization.getDescription(), output.getDescription());
        assertEquals(organization.getUseMasterKeys(), output.getUseMasterKeys());
        assertEquals(OrganizationType.master.toString(), output.getType());
    }

    @Test
    void testGetKeyMetadata() throws Exception {
        // GIVEN
        String organizationId = "master";

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(ORGANIZATIONS_URL + "/" + organizationId + "/keys")
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
    void testGetComponents() throws Exception {
        // GIVEN
        String organizationId = "master";

        // WHEN
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(ORGANIZATIONS_URL + "/" + organizationId + "/components")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
        ResponseBody responseBody = response.getBody();

        List<ComponentRepresentation> output = new ObjectMapper().readValue(responseBody.asInputStream(), List.class);
        assertNotNull(output);
        assertFalse(output.isEmpty());
    }
}
