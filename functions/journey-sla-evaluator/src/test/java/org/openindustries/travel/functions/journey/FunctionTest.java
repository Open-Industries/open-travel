package org.openindustries.travel.functions.journey;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"bookingId\":\"bk-1\",\"measuredDelayMinutes\":15,\"targetDelayMinutes\":60,\"measuredConnectionSuccessPct\":99.0,\"targetConnectionSuccessPct\":98.0}")
                .when().post("/journeySlaEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"bookingId\":\"bk-2\",\"measuredDelayMinutes\":240,\"targetDelayMinutes\":60,\"measuredConnectionSuccessPct\":99.0,\"targetConnectionSuccessPct\":98.0}")
                .when().post("/journeySlaEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
