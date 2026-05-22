package com.martinsladek.demo.routeplanner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martinsladek.demo.routeplanner.model.Country;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {

    private final Map<String, Country> byCode = new HashMap<>();
    private final Map<String, List<String>> graph = new HashMap<>();
    private final Map<String, Map<String, Double>> weightedGraph = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL url = new URL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json");
        Country[] countries = mapper.readValue(url, Country[].class);

        for (Country c : countries) {
            graph.put(c.getCca3(), c.getBorders());
        }

        // index by cca3
        for (Country c : countries) {
            byCode.put(c.getCca3(), c);
        }

        // build weighted graph
        for (Country c : countries) {
            Map<String, Double> neighbors = new HashMap<>();

            for (String border : c.getBorders()) {
                Country neighbor = byCode.get(border);
                if (neighbor != null) {
                    double distance = haversine(c.getLat(), c.getLng(), neighbor.getLat(), neighbor.getLng());
                    neighbors.put(border, distance);
                }
            }
            weightedGraph.put(c.getCca3(), neighbors);
        }
    }

    public Map<String, List<String>> getGraph() {
        return graph;
    }

    public Map<String, Map<String, Double>> getWeightedGraph() {
        return weightedGraph;
    }

    public boolean exists(String code) {
        return byCode.containsKey(code);
    }

    // Haversine formula
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6378; // km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public Map<String, Country> getByCode() {
        return byCode;
    }
}
