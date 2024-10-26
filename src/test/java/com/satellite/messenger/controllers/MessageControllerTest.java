package com.satellite.messenger.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@QuarkusTest
class MessageControllerTest {
    @Test
    void whenDecode_ThenResultIsOk() {

        final String request = """
        {
            "satellites": [
                {
                    "name": "satelliteA",
                    "distance": 600,
                    "message": ["", "Este", "es", "", "mensaje"]
                },
                {
                    "name": "satelliteB",
                    "distance": 115.50,
                    "message": ["Este", "", "un", "mensaje"]
                },
                {
                    "name": "satelliteC",
                    "distance": 109.86,
                    "message": ["", "Este", "", "", "mensaje"]
                }
            ]
        }
        """;

        final String response = "{\"position\":{\"x\":99.8,\"y\":-215.5},\"message\":\"Este es un mensaje\"}";

        given()
            .body(request)
            .contentType(APPLICATION_JSON_VALUE)
            .when()
            .post("/messages/decode")
            .then()
            .statusCode(200)
            .contentType(containsString(APPLICATION_JSON_VALUE))
            .body(is(response));
    }
    @Test
    void whenDecode_ThenResultIsNotOk() {

        final String request = """
        {
            "satellites": [
                {
                    "name": "satelliteA",
                    "distance": 600,
                    "message": ["", "Este", "es", "", "mensaje"]
                },
                {
                    "name": "satelliteB",
                    "distance": 115.50,
                    "message": ["Este", "", "un", "mensaje"]
                },
                {
                    "name": "satelliteC",
                    "distance": 100.00,
                    "message": ["", "Este", "", "", "mensaje"]
                }
            ]
        }
        """;

        given()
                .body(request)
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .post("/messages/decode")
                .then()
                .statusCode(400);
    }

}