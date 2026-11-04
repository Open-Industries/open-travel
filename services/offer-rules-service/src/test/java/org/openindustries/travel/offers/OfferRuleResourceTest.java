package org.openindustries.travel.offers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class OfferRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"economy-standard\",\"cabinClass\":\"ECONOMY\",\"maxDiscountMinor\":20000,\"maxStopovers\":2,\"priority\":1}")
                .when().post("/api/v1/offer-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/offer-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"cabinClass\":\"ECONOMY\",\"requiredMaxStopovers\":3,\"requiredMaxDiscountMinor\":15000}")
                .when().post("/api/v1/offer-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"cabinClass\":\"FIRST\",\"requiredMaxStopovers\":3,\"requiredMaxDiscountMinor\":15000}")
                .when().post("/api/v1/offer-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
