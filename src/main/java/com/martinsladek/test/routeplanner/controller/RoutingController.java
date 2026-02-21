package com.martinsladek.test.routeplanner.controller;

import com.martinsladek.test.routeplanner.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/routing/{origin}/{destination}")
    public ResponseEntity<?> route(@PathVariable String origin,
                                   @PathVariable String destination) {

        List<String> route = routingService.findRoute(origin, destination);

        if (route == null) {
            return ResponseEntity.badRequest().body("No land route found");
        }

        return ResponseEntity.ok(Map.of("route", route));
    }

    @GetMapping("/routingWeighted/{origin}/{destination}")
    public ResponseEntity<?> routeWeighted(@PathVariable String origin,
                                   @PathVariable String destination) {

        List<String> route = routingService.findRouteWeighted(origin, destination);

        if (route == null) {
            return ResponseEntity.badRequest().body("No land route found");
        }

        return ResponseEntity.ok(Map.of("route", route));
    }
}
