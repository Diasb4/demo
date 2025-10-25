package com.example.Report;

import java.util.List;

import com.example.Graph.Edge;

public class MSTResult {
    public List<Edge> mstEdges;
    public int totalCost;
    public int operationsCount;
    public long executionTimeMs;

    public MSTResult(List<Edge> mstEdges, int totalCost,
            int operationsCount, long executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }
}
