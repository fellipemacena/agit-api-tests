package br.com.dogapi.core;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public final class BaseConfig {

    private static final String DEFAULT_BASE_URL = "https://dog.ceo/api";

    private BaseConfig() {
    }

    public static void configure() {
        String baseUrl = System.getProperty("DOG_API_BASE_URL");
        RestAssured.baseURI = (baseUrl == null || baseUrl.isBlank()) ? DEFAULT_BASE_URL : baseUrl;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
