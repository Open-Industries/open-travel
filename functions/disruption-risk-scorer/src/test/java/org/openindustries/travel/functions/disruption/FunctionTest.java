package org.openindustries.travel.functions.disruption;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"bookingId\":\"bk-1\",\"weatherIndex\":10,\"congestionIndex\":14,\"strikeIndex\":0}")
                .when().post("/disruptionRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"bookingId\":\"bk-2\",\"weatherIndex\":85,\"congestionIndex\":72,\"strikeIndex\":68}")
                .when().post("/disruptionRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
