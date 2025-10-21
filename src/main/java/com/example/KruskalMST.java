package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {
    public static MSTResult run(Graph graph) {
        long start = System.currentTimeMillis();
        List<Edge> result = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        DisjointSet ds = new DisjointSet();
        ds.makeSet(graph.vertices);

        List<Edge> sortedEdges = new ArrayList<>(graph.edges);
        Collections.sort(sortedEdges);

        for (Edge e : sortedEdges) {
            operations++;
            String root1 = ds.find(e.from);
            String root2 = ds.find(e.to);
            if (!root1.equals(root2)) {
                result.add(e);
                totalCost += e.weight;
                ds.union(root1, root2);
            }
        }

        long end = System.currentTimeMillis();
        return new MSTResult(result, totalCost, operations, end - start);
    }
}
