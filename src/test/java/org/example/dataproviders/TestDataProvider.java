package org.example.dataproviders;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {
    @DataProvider(name = "citiesFromFile")
    public static Object[][] citiesFromFile() throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/testdata/cities.csv"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.trim().split(",");
                if (parts.length >= 1) {
                    data.add(new Object[]{parts[0]});
                }
            }

        }
        return data.toArray(new Object[0][0]);
    }

    @DataProvider(name = "citiesAndLangsFromFile")
    public static Object[][] citiesAndLangsFromFile() throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/testdata/cities.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length == 2) {
                    data.add(new Object[]{parts[0], parts[1]});
                }
            }
        }
        return data.toArray(new Object[0][0]);
    }
}
