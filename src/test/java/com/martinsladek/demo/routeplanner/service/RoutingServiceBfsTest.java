package com.martinsladek.demo.routeplanner.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RoutingServiceBfsTest {

    @Test
    void testSimpleBfs() {
        // mock CountryService
        CountryService countryService = Mockito.mock(CountryService.class);

        // graph for BFS
        Map<String, List<String>> graph = Map.of(
                "A", List.of("B"),
                "B", List.of("A", "C"),
                "C", List.of("B")
        );

        Mockito.when(countryService.getGraph()).thenReturn(graph);

        RoutingService routingService = new RoutingService(countryService);

        var route = routingService.findRoute("A", "C");

        assertThat(route).containsExactly("A", "B", "C");
    }

    @Test
    void testNoRoute() {
        CountryService countryService = Mockito.mock(CountryService.class);

        Map<String, List<String>> graph = Map.of(
                "A", List.of("B"),
                "B", List.of("A"),
                "C", List.of() // isolated
        );

        Mockito.when(countryService.getGraph()).thenReturn(graph);

        RoutingService routingService = new RoutingService(countryService);

        var route = routingService.findRoute("A", "C");

        assertThat(route).isNull();
    }
}
