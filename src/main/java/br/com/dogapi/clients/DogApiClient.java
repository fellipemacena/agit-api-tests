package br.com.dogapi.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DogApiClient {

    public Response getAllBreeds() {
        return given()
                .when()
                .get("/breeds/list/all");
    }

    public Response getBreedImages(String breed) {
        return given()
                .when()
                .get("/breed/{breed}/images", breed);
    }

    public Response getRandomImage() {
        return given()
                .when()
                .get("/breeds/image/random");
    }
}
