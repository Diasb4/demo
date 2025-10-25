package com.example.Graph;

public class Edge implements Comparable<Edge> {
    public String from;
    public String to;
    public int weight;

    public Edge(String from, String to, int weight) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Vertex names cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return from + " - " + to + " : " + weight;
    }
}