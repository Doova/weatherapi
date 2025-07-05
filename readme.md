OpenWeatherMap API Test Project

This is an automated API testing project using Java, TestNG, and RestAssured to validate responses from the OpenWeatherMap Current Weather API.

Features

Data-driven testing using TestNG and CSV files

Positive and negative tests for API endpoints

API key management via config file

Modular structure using Base classes and helpers

Allure integration for beautiful reports

Test Coverage

Positive Tests

Basic status code verification for multiple cities (from CSV)

Parameterized testing for different cities and metric units

Response field validation (e.g., temperature, humidity)

Validations for non-null essential fields

Negative Tests

Missing API key (401 Unauthorized)

Invalid city name (404 Not Found)

Empty or malformed query (400 Bad Request)

Optional (Planned / Extensible)

Language-specific responses validation

Comparison of description field between different languages

Schema validation using JSON Schema

Project Structure

src
├── main
│   └── java
│       └── org.example
│           ├── base               # BaseTest with common setup
│           ├── helpers            # Utilities for logging and validation
│           ├── utils              # Config reader and Allure logger
├── test
│   ├── java
│   │   └── org.example
│   │       ├── dataproviders     # CSV-based data providers
│   │       └── tests             # TestNG test classes
│   └── resources
│       ├── config.properties     # API key stored here
│       └── testdata
│           └── cities.csv        # City-country pairs used for testing

Configuration

config.properties

api.key=YOUR_OPENWEATHERMAP_API_KEY

cities.csv

Moscow,RU
London,GB
New York,EN
Tokyo,JP
Berlin,DE
Tbilisi,GE
Paris,FR

How to Run

Run tests via Maven:

mvn clean test

Generate Allure Report:

allure serve target/allure-results

Requirements

Java 11+

Maven

Allure CLI (for reports)

Notes

API key must be valid and have access to the Current Weather endpoint

Some tests (e.g., language validation) are sensitive to live data

Consider using mocks or recorded responses for CI stability

Contact / Contribute

Feel free to fork and extend. For questions, reach out via GitHub or any channel.