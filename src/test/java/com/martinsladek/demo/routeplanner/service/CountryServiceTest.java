package com.martinsladek.demo.routeplanner.service;

import com.martinsladek.demo.routeplanner.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    void testCountryDataLoaded() {
        Map<String, Country> byCode = countryService.getByCode();

        assertFalse(byCode.isEmpty(), "Country dataset should not be empty");
        assertTrue(byCode.containsKey("CZE"), "Dataset should contain CZE");
        assertTrue(byCode.containsKey("ITA"), "Dataset should contain ITA");
    }
}
