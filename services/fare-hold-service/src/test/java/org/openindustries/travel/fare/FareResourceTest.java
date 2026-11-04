package org.openindustries.travel.fare;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FareResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"bookingId\":\"11111111-1111-1111-1111-111111111111\",\"heldAmountMinor\":185000}")
                .when().post("/api/v1/fare-holds")
                .then().statusCode(201).body("heldAmountMinor", is(185000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"chargeType\":\"FARE\",\"amountMinor\":120000}")
                .when().post("/api/v1/fare-holds/" + id + "/charges")
                .then().statusCode(200)
                .body("remainingAfter", is(65000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"bookingId\":\"11111111-1111-1111-1111-111111111111\",\"heldAmountMinor\":185000}")
                .when().post("/api/v1/fare-holds")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"chargeType\":\"FARE\",\"amountMinor\":400000}")
                .when().post("/api/v1/fare-holds/" + id + "/charges")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/fare-holds/" + id)
                .then().statusCode(200).body("heldAmountMinor", is(185000));
    }
}
