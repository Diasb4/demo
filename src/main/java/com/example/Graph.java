package com.example;

import java.util.List;

public class Graph {
    List<String> vertices;
    List<Edge> edges;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}