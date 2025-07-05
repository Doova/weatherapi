package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class NegativeTests extends BaseTest {
    @Test(groups = {"regression","logging"})
    public void testNoAuth() {
        RequestSpecification customRequest = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .queryParam("q", "Tbilisi");

        response = customRequest
                .when()
                .get("weather");

        response
                .then()
                .statusCode(401)
                .body("message", equalToIgnoringCase("Invalid API key. Please see https://openweathermap.org/faq#error401 for more info."));
    }


    @Test(groups = {"regression","logging"})
    public void testWrongCity() {

        request
                .queryParam("q", "NQSC");

        response = request
                .when()
                .get("weather");

        response
                .then()
                .statusCode(404)
                .body("message", equalToIgnoringCase("city not found"));
    }

    @Test(groups = {"regression","logging"})
    public void testEmptyCity() {

        request
                .queryParam("q", "");

        response = request
                .when()
                .get("weather");

        response
                .then()
                .statusCode(400)
                .body("message", equalToIgnoringCase("Nothing to geocode"));
    }
}
