package org.heg;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class PersonControllerTest {
    @Test
    void testPersonsEndpoint() {
        given()
                .when().get("/persons")
                .then()
                .statusCode(200);
    }
}