package org.openindustries.travel.hotel;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TravelPropertyResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Hotel Mar Azul\",\"propertyType\":\"HOTEL\",\"roomsCount\":120}")
                .when().post("/api/v1/travel-properties")
                .then().statusCode(201)
                .body("status", is("ACTIVE"))
                .body("propertyType", is("HOTEL"))
                .extract().path("id");

        given().when().post("/api/v1/travel-properties/" + id + "/suspend")
                .then().statusCode(200).body("status", is("SUSPENDED"));
    }
}
