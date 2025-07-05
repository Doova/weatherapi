package org.example.tests;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.example.helpers.*;

import static org.example.utils.AllureLogger.logCityLangAndDescription;
import static org.example.utils.AllureLogger.logComparison;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class PositiveTests extends BaseTest {

    @Test(groups = {"regression","logging"})
    public void testPositiveResponse() {

        request
                .queryParam("q", "Tbilisi");

        response = request
                .when()
                .get("weather");

        response
                .then()
                .statusCode(200);
        ResponseLogger.logIfError(response, "testWrongCity");
        Allure.addAttachment("Response", "application/json", response.asString());
    }

    @Test(groups = {"regression","logging"}, dataProvider = "citiesFromFile", dataProviderClass = org.example.dataproviders.TestDataProvider.class)
    public void testGetWeatherWithQueryParams(String city) {

        request
                .queryParam("q", city)
                .queryParam("units", "metric");

        response = request
                .when()
                .get("weather");

        response
                .then()
                .statusCode(200)
                .body("name", equalToIgnoringCase(city))
                .body("main.temp", notNullValue());
        ResponseLogger.logIfError(response, "testWrongCity");
        Allure.addAttachment("Response", "application/json", response.asString());
    }

    @Test(groups = {"regression","logging"})
    public void testResponseAndFields() {

        request
                .queryParam("q", "Moscow");

        response = request
                .when()
                .get("weather");

        response
                .then()
                .statusCode(200);
        WeatherResponseValidator.validateStandardFields(response);
        ResponseLogger.logIfError(response, "testWrongCity");
        Allure.addAttachment("Response", "application/json", response.asString());
    }

    @Test(groups = {"regression","logging"}, dataProvider = "citiesFromFile", dataProviderClass = org.example.dataproviders.TestDataProvider.class)
    public void testGetWeatherCheckTempAndHum(String city) {

        request
                .queryParam("q", city);

        response = request
                .when()
                .get("weather");

        response
                .then()
                .statusCode(200)
                .body("name", equalToIgnoringCase(city))
                .body("main.temp", greaterThanOrEqualTo(0.0f))
                .body("main.humidity", greaterThanOrEqualTo(0));
        ResponseLogger.logIfError(response, "testWrongCity");
        Allure.addAttachment("Response", "application/json", response.asString());
    }

    @Test(groups = {"regression","logging"}, dataProvider = "citiesAndLangsFromFile", dataProviderClass = org.example.dataproviders.TestDataProvider.class)
    public void testLocalizedDescriptionFromCSV(String city, String lang) {
        response = request
                .queryParam("q", city)
                .queryParam("lang", lang)
                .queryParam("appid", ConfigReader.getApiToken())
                .when().get("weather");

        String description = response.then().statusCode(200)
                .extract().path("weather[0].description");
        Allure.addAttachment("Response", "application/json", response.asString());

        logCityLangAndDescription(city, lang, description);

        Assert.assertNotNull(description);
        Assert.assertTrue(description.length() > 2);
    }

    @Test(groups = {"regression"})
    public void testDescriptionChangesWithLang() {
        String city = "Paris";

        // RU
        response = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .queryParam("q", city)
                .queryParam("lang", "ru")
                .queryParam("appid", ConfigReader.getApiToken())
                .when().get("weather");
        // RU

        String descRu = response.then().statusCode(200)
                .extract().path("weather[0].description");
        Allure.addAttachment("Response RU", "application/json", response.asString());

        logCityLangAndDescription(city, "ru", descRu); // 🟢 В Allure

        // EN
        response = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .queryParam("q", city)
                .queryParam("lang", "en")
                .queryParam("appid", ConfigReader.getApiToken())
                .when().get("weather");

        String descEn = response.then().statusCode(200)
                .extract().path("weather[0].description");
        Allure.addAttachment("Response EN", "application/json", response.asString());

        logCityLangAndDescription(city, "en", descEn);

        logComparison("ru", descRu, "en", descEn);

        Assert.assertNotEquals(descRu.toLowerCase(), descEn.toLowerCase(),
                "Descriptions should differ between languages");
    }


}