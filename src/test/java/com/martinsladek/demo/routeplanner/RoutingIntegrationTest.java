package com.martinsladek.demo.routeplanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoutingIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testRouteCzeToIta() {
        var response = rest.getForEntity("/routing/CZE/ITA", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("CZE", "AUT", "ITA");
    }

    @Test
    void testRouteImpossible() {
        var response = rest.getForEntity("/routing/CZE/USA", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
