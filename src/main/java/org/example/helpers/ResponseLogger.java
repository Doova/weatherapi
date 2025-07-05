package org.example.helpers;

import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseLogger {

    public static void logIfError(Response response, String testName) {
        if (response.getStatusCode() >= 400) {
            try {
                String content = response.asPrettyString();
                Files.createDirectories(Path.of("logs"));
                Files.writeString(Path.of("logs/" + testName + ".json"), content);
                System.out.println("Response logged to logs/" + testName + ".json");
            } catch (IOException e) {
                System.out.println("Failed to write response log: " + e.getMessage());
            }
        }
    }
}
