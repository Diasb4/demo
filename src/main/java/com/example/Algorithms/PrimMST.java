package com.example.Algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.example.Graph.Edge;
import com.example.Graph.Graph;
import com.example.Report.MSTResult;

public class PrimMST {
    public static MSTResult run(Graph graph) {
        if (graph.vertices == null || graph.vertices.isEmpty()) {
            return new MSTResult(new ArrayList<>(), 0, 0, 0);
        }
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