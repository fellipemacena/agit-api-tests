package br.com.dogapi.tests;

import br.com.dogapi.clients.DogApiClient;
import br.com.dogapi.core.BaseConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DogApiTest {

    private static DogApiClient dogApiClient;

    @BeforeAll
    static void setUp() {
        BaseConfig.configure();
        dogApiClient = new DogApiClient();
    }

    @Test
    @DisplayName("Cenario 1 - Validar GET /breeds/list/all")
    void shouldValidateGetBreedsListAll() {
        Response response = dogApiClient.getAllBreeds();
        JsonPath json = response.jsonPath();

        assertEquals(200, response.statusCode());
        assertEquals("success", json.getString("status"));
        assertNotNull(json.get("message"));

        Map<String, List<String>> breeds = json.getMap("message");
        assertNotNull(breeds);
        assertFalse(breeds.isEmpty());
    }

    @Test
    @DisplayName("Cenario 2 - Validar GET /breed/hound/images")
    void shouldValidateGetBreedHoundImages() {
        Response response = dogApiClient.getBreedImages("hound");
        JsonPath json = response.jsonPath();

        assertEquals(200, response.statusCode());
        assertEquals("success", json.getString("status"));

        List<String> images = json.getList("message");
        assertNotNull(images);
        assertFalse(images.isEmpty());
        assertTrue(images.stream().allMatch(this::isValidImageUrl));
        assertTrue(images.stream().anyMatch(url -> url.toLowerCase().contains("hound")));
    }

    @Test
    @DisplayName("Cenario 3 - Validar GET /breeds/image/random")
    void shouldValidateGetBreedsImageRandom() {
        Response response = dogApiClient.getRandomImage();
        JsonPath json = response.jsonPath();

        assertEquals(200, response.statusCode());
        assertEquals("success", json.getString("status"));

        String imageUrl = json.getString("message");
        assertNotNull(imageUrl);
        assertTrue(isValidImageUrl(imageUrl));
    }

    @Test
    @DisplayName("Cenario 4 - Validar comportamento real para raca invalida")
    void shouldValidateInvalidBreedBehavior() {
        Response response = dogApiClient.getBreedImages("invalidbreed");
        JsonPath json = response.jsonPath();

        assertEquals(404, response.statusCode());
        assertEquals("error", json.getString("status"));
        assertNotNull(json.getString("message"));
        assertEquals(404, json.getInt("code"));
    }

    private boolean isValidImageUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        return url.matches("(?i)^https://.+\\.(jpg|jpeg|png|webp)$");
    }
}
