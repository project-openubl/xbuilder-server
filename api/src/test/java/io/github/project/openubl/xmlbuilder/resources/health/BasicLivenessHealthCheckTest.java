package io.github.project.openubl.xmlbuilder.resources.health;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BasicLivenessHealthCheckTest {

    @Test
    public void testEndpoint() {
        given()
                .when().get("/health/live")
                .then()
                .statusCode(200)
                .body("status", is("UP"))
                .body("checks.size()", is(1))
                .body("checks[0].status", is("UP"))
                .body("checks[0].name", is("Server liveness running"));
    }
}
