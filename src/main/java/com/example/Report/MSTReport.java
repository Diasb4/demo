package com.example.Report;

import java.util.List;

public class MSTReport {
    public int graphId;
    public int vertices;
    public int edges;
    public MSTResult primResult;
    public MSTResult kruskalResult;

    public MSTReport(int graphId, int vertices, int edges,
            MSTResult primResult, MSTResult kruskalResult) {
        this.graphId = graphId;
        this.vertices = vertices;
        this.edges = edges;
        this.primResult = primResult;
        this.kruskalResult = kruskalResult;
    }
}
