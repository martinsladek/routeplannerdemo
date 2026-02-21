package com.martinsladek.test.routeplanner.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoutingServiceTest {

    @Autowired
    private RoutingService routingService;

    @Test
    void testSimpleRouteCzeToIta() {
        List<String> route = routingService.findRoute("CZE", "ITA");

        assertNotNull(route, "Route should exist");
        assertEquals("CZE", route.get(0));
        assertEquals("ITA", route.get(route.size() - 1));
    }

    @Test
    void testNoRouteBetweenCountries() {
        List<String> route = routingService.findRoute("USA", "AUS");

        assertNull(route, "There should be no land route between USA and AUS");
    }
}
