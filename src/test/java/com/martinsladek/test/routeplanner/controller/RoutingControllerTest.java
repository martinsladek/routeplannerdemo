package com.martinsladek.test.routeplanner.controller;

import com.martinsladek.test.routeplanner.service.RoutingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoutingControllerTest {

    @Autowired
    private RoutingController controller;

    @Autowired
    private RoutingService routingService;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(routingService);
    }
}
