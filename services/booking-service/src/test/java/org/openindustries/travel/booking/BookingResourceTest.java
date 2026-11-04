package org.openindustries.travel.booking;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BookingResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"bookingType\":\"FLIGHT\",\"pnrCode\":\"PNR-XK7P2Q\",\"channelCode\":\"WEB\"}")
                .when().post("/api/v1/bookings")
                .then().statusCode(201)
                .body("status", is("HELD"))
                .body("pnrCodeMasked", endsWith("7P2Q"))
                .extract().path("id");

        given().when().get("/api/v1/bookings/" + id)
                .then().statusCode(200).body("status", is("HELD"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"bookingType\":\"FLIGHT\",\"pnrCode\":\"PNR-XK7P2Q\",\"channelCode\":\"WEB\"}")
                .when().post("/api/v1/bookings")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/bookings/" + id + "/confirm")
                .then().statusCode(200).body("status", is("CONFIRMED"));
    }
}
