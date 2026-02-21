package com.martinsladek.test.routeplanner.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void testCountryJsonMapping() throws IOException {
        String json = """
            {
              "cca3": "CZE",
              "borders": ["AUT", "DEU", "POL", "SVK"],
              "latlng": [49.75, 15.5],
              "capitalInfo": {
                "latlng": [50.08, 14.44]
              }
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        Country c = mapper.readValue(json, Country.class);

        assertEquals("CZE", c.getCca3());
        assertEquals(4, c.getBorders().size());
        assertEquals("AUT", c.getBorders().get(0));

        // capital coordinates should override centroid
        assertEquals(50.08, c.getLat(), 0.001);
        assertEquals(14.44, c.getLng(), 0.001);
    }
}
