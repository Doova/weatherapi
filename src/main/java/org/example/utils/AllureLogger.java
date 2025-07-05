package org.example.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class AllureLogger {

    @Step("City: {city}, Language: {lang}, Weather description: {description}")
    public static void logCityLangAndDescription(String city, String lang, String description) {
        // Можно добавить дополнительное логирование в консоль или файл, если нужно
    }

    @Step("Compare same city name on diff languages")
    public static void logComparison(String lang1, String desc1, String lang2, String desc2) {
        String message = String.format(
                "Lang1: %s → %s\nLang2: %s → %s\n",
                lang1, desc1, lang2, desc2
        );
        Allure.addAttachment("Language Comparison", message);
    }
}
