package org.example.base;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.utils.ConfigReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * Base class for all api tests
 * Contains common settings and configuration
 */
public class BaseTest {

    protected RequestSpecification request;
    protected Response response;

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        // Global rest assured setting
        RestAssured.baseURI = "https://api.openweathermap.org";
        RestAssured.port = 443;
        RestAssured.basePath = "/data/2.5/";

        // Logging for debug
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        System.out.println("API Test Suite initialized");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        // Request initialization before every test
        request = RestAssured.given()
                .contentType("application/json")
                .accept("application/json");
                addAuth(ConfigReader.getApiToken());
    }

    @AfterMethod(onlyForGroups = {"logging"},alwaysRun = true)
    public void responseLogger() {
        if (response != null) {
            Allure.addAttachment("Response", "application/json", response.asString());
        }
    }

    // Universal method for auth
    protected void addAuth(String token) {
        request = request.queryParam("appid", token);
    }

     // Method for adding headers
    protected void addHeaders(String key, String value) {
        request = request.header(key, value);
    }
}