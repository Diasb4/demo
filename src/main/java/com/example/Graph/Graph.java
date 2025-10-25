package com.example.Graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private long id;
    public List<String> vertices;
    public List<Edge> edges;

    public Graph(long id, List<String> vertices, List<Edge> edges) {
        this.id = id;
        this.vertices = vertices;
        this.edges = edges;
    }

    public long getId() {
        return id;
    }

    // Удобный метод для добавления ребра
    public void addEdge(String from, String to, int weight) {
        if (from == null || to == null)
            throw new IllegalArgumentException("Vertex name cannot be null");
        if (weight < 0)
            throw new IllegalArgumentException("Edge weight cannot be negative");
        this.edges.add(new Edge(from, to, weight));
    }
}
