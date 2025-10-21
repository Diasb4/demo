package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class PrimMST {
    public static MSTResult run(Graph graph) {
        long start = System.currentTimeMillis();
        List<Edge> mstEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        int operations = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        String startVertex = graph.vertices.get(0);
        visited.add(startVertex);
        for (Edge e : graph.edges) {
            if (e.from.equals(startVertex) || e.to.equals(startVertex))
                pq.add(e);
            operations++;
        }

        int totalCost = 0;

        while (!pq.isEmpty() && visited.size() < graph.vertices.size()) {
            Edge e = pq.poll();
            if (visited.contains(e.from) && visited.contains(e.to))
                continue;
            operations++;

            mstEdges.add(e);
            totalCost += e.weight;

            String next = visited.contains(e.from) ? e.to : e.from;
            visited.add(next);
            operations++;

            for (Edge edge : graph.edges) {
                if ((edge.from.equals(next) && !visited.contains(edge.to)) ||
                        (edge.to.equals(next) && !visited.contains(edge.from))) {
                    pq.add(edge);
                    operations++;
                }
                operations++;
            }
        }

        long end = System.currentTimeMillis();
        return new MSTResult(mstEdges, totalCost, operations, end - start);

    }
}