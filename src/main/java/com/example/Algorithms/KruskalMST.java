package com.example.Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.Graph.DisjointSet;
import com.example.Graph.Edge;
import com.example.Graph.Graph;
import com.example.Report.MSTResult;

public class KruskalMST {
    public static MSTResult run(Graph graph) {
        if (graph.vertices == null || graph.vertices.isEmpty()) {
            return new MSTResult(new ArrayList<>(), 0, 0, 0);
        }

        long start = System.currentTimeMillis();
        List<Edge> result = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        DisjointSet ds = new DisjointSet();
        ds.makeSet(graph.vertices);
        operations += graph.vertices.size(); // операции инициализации множеств

        List<Edge> sortedEdges = new ArrayList<>(graph.edges);

        // Сортировка рёбер — учитываем сложность O(E log E)
        Collections.sort(sortedEdges);
        operations += sortedEdges.size() * (int) (Math.log(sortedEdges.size()) / Math.log(2));

        for (Edge e : sortedEdges) {
            operations++; // проверка ребра
            String root1 = ds.find(e.from);
            String root2 = ds.find(e.to);
            operations += 2; // два find

            if (!root1.equals(root2)) {
                result.add(e);
                totalCost += e.weight;
                ds.union(root1, root2);
                operations++; // один union
            }
        }

        long end = System.currentTimeMillis();
        return new MSTResult(result, totalCost, operations, end - start);
    }
}
