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
package io.github.project.openubl.xmlbuilder.resources.health;

import io.github.project.openubl.xmlbuilder.resources.AbstractResourceTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class BasicLivenessHealthCheckIT extends AbstractResourceTest {

    URL managementURL;

    @ArquillianResource
    ManagementClient managementClient;

    @Before
    public void before() throws MalformedURLException {
        managementURL = new URL(String.format("http://%s:%d", managementClient.getMgmtAddress(), managementClient.getMgmtPort()));
    }

    @Test
    public void testEndpoint() {
        given()
                .when().get(managementURL.toString() + "/health/live")
                .then()
                .statusCode(200)
                .body("status", is("UP"))
                .body("checks.size()", is(1))
                .body("checks[0].status", is("UP"))
                .body("checks[0].name", is("Server liveness running"));
    }
}
