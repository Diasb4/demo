package com.example;

import org.junit.Test;

import com.example.Algorithms.KruskalMST;
import com.example.Algorithms.PrimMST;
import com.example.Graph.Edge;
import com.example.Graph.Graph;
import com.example.Report.MSTResult;
import java.util.*;

import static org.junit.Assert.*;

public class AppTest {

    private Graph createSimpleGraph() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C", "D"), new ArrayList<>());
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 3);
        graph.addEdge("B", "D", 4);
        graph.addEdge("C", "D", 5);
        return graph;
    }

    private boolean isAcyclic(List<Edge> edges, int vertexCount) {
        Map<String, String> parent = new HashMap<>();

        for (Edge e : edges) {
            String rootFrom = find(e.from, parent);
            String rootTo = find(e.to, parent);
            if (rootFrom.equals(rootTo))
                return false;
            parent.put(rootFrom, rootTo);
        }
        return true;
    }

    private String find(String node, Map<String, String> parent) {
        if (!parent.containsKey(node))
            parent.put(node, node);
        if (!parent.get(node).equals(node))
            parent.put(node, find(parent.get(node), parent));
        return parent.get(node);
    }

    private boolean isConnected(List<Edge> edges, List<String> vertices) {
        if (vertices.isEmpty())
            return true;

        Set<String> visited = new HashSet<>();
        Map<String, List<String>> adj = new HashMap<>();
        for (Edge e : edges) {
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e.to);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(e.from);
        }

        Deque<String> stack = new ArrayDeque<>();
        stack.push(vertices.get(0));
        while (!stack.isEmpty()) {
            String v = stack.pop();
            if (!visited.add(v))
                continue;
            for (String n : adj.getOrDefault(v, Collections.emptyList())) {
                if (!visited.contains(n))
                    stack.push(n);
            }
        }

        return visited.size() == vertices.size();
    }

    @Test
    public void testMSTCorrectnessAndConsistency() {
        Graph graph = createSimpleGraph();

        MSTResult kruskalResult = KruskalMST.run(graph);
        MSTResult primResult = PrimMST.run(graph);

        // a.1 Same total cost
        assertEquals(kruskalResult.totalCost, primResult.totalCost, 0.001);

        // a.2 Number of edges = V - 1
        assertEquals(graph.vertices.size() - 1, kruskalResult.mstEdges.size());
        assertEquals(graph.vertices.size() - 1, primResult.mstEdges.size());

        // a.3 MSTs are acyclic
        assertTrue(isAcyclic(kruskalResult.mstEdges, graph.vertices.size()));
        assertTrue(isAcyclic(primResult.mstEdges, graph.vertices.size()));

        // a.4 MSTs connect all vertices
        assertTrue(isConnected(kruskalResult.mstEdges, graph.vertices));
        assertTrue(isConnected(primResult.mstEdges, graph.vertices));

        // b.1 Non-negative execution time
        assertTrue(kruskalResult.executionTimeMs >= 0);
        assertTrue(primResult.executionTimeMs >= 0);

        // b.2 Non-negative operation counts
        assertTrue(kruskalResult.operationsCount >= 0);
        assertTrue(primResult.operationsCount >= 0);

        // b.3 Reproducibility
        MSTResult secondRun = KruskalMST.run(graph);
        assertEquals(kruskalResult.totalCost, secondRun.totalCost, 0.001);
        assertEquals(kruskalResult.mstEdges.size(), secondRun.mstEdges.size());
    }

    @Test
    public void testDisconnectedGraphHandledGracefully() {
        Graph graph = new Graph(2, Arrays.asList("A", "B", "C"), new ArrayList<>());
        graph.addEdge("A", "B", 2);
        // "C" isolated — disconnected graph

        MSTResult kruskalResult = KruskalMST.run(graph);
        MSTResult primResult = PrimMST.run(graph);

        // should produce either no MST or fewer than V−1 edges
        assertTrue(kruskalResult.mstEdges.size() < graph.vertices.size() - 1);
        assertTrue(primResult.mstEdges.size() < graph.vertices.size() - 1);
    }

    @Test
    public void testPerformanceAndReproducibility() {
        Graph graph = createSimpleGraph();
        MSTResult first = PrimMST.run(graph);
        MSTResult second = PrimMST.run(graph);

        assertEquals(first.totalCost, second.totalCost, 0.001);
        assertEquals(first.mstEdges.size(), second.mstEdges.size());
    }

    @Test
    public void sameTotalCostTest() {
        Graph graph = createSimpleGraph();
        MSTResult kruskalResult = KruskalMST.run(graph);
        MSTResult primResult = PrimMST.run(graph);

        assertEquals(kruskalResult.totalCost, primResult.totalCost, 0.001);

    }

}
