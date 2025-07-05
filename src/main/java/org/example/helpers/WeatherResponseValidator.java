package org.example.helpers;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;

public class WeatherResponseValidator {

    public static void validateStandardFields(Response response) {
        response
                .then()
                .body("coord", notNullValue())
                .body("weather", notNullValue())
                .body("base", notNullValue())
                .body("main", notNullValue())
                .body("visibility", notNullValue())
                .body("clouds", notNullValue())
                .body("dt", notNullValue())
                .body("sys", notNullValue())
                .body("timezone", notNullValue())
                .body("id", notNullValue())
                .body("name", notNullValue())
                .body("cod", notNullValue());
    }
}
