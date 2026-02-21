package com.martinsladek.test.routeplanner.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutingService {

    private final CountryService countryService;

    public RoutingService(CountryService countryService) {
        this.countryService = countryService;
    }

    public List<String> findRoute(String origin, String destination) {

        Map<String, List<String>> graph = countryService.getGraph();

        if (!graph.containsKey(origin) || !graph.containsKey(destination)) {
            return null;
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(destination)) {
                return buildPath(parent, origin, destination);
            }

            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return null;
    }

    public List<String> findRouteWeighted(String origin, String destination) {

        if (!countryService.exists(origin) || !countryService.exists(destination)) {
            return null;
        }

        Map<String, Map<String, Double>> graph = countryService.getWeightedGraph();

        // Dijkstra
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (String node : graph.keySet()) {
            dist.put(node, Double.MAX_VALUE);
        }

        dist.put(origin, 0.0);
        pq.add(origin);

        while (!pq.isEmpty()) {
            String current = pq.poll();

            if (current.equals(destination)) {
                return buildPath(prev, origin, destination);
            }

            for (Map.Entry<String, Double> edge : graph.get(current).entrySet()) {
                String neighbor = edge.getKey();
                double weight = edge.getValue();

                double newDist = dist.get(current) + weight;

                if (newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        return null;
    }

    private List<String> buildPath(Map<String, String> parent, String origin, String destination) {
        LinkedList<String> path = new LinkedList<>();
        String step = destination;

        while (step != null) {
            path.addFirst(step);
            step = parent.get(step);
        }

        return path;
    }
}
