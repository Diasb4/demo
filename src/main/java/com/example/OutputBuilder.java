package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.util.List;
import com.example.MSTResult;
import com.example.Edge;

public class OutputBuilder {

    public static void saveResults(List<MSTReport> reports) {
        JSONArray resultsArray = new JSONArray();

        for (MSTReport report : reports) {
            JSONObject graphObj = new JSONObject();
            graphObj.put("graph_id", report.graphId);

            JSONObject inputStats = new JSONObject();
            inputStats.put("vertices", report.vertices);
            inputStats.put("edges", report.edges);
            graphObj.put("input_stats", inputStats);

            graphObj.put("prim", buildAlgorithmSection(report.primResult));
            graphObj.put("kruskal", buildAlgorithmSection(report.kruskalResult));

            resultsArray.add(graphObj);
        }

        JSONObject finalOutput = new JSONObject();
        finalOutput.put("results", resultsArray);

        try (FileWriter file = new FileWriter("output.json")) {
            file.write(finalOutput.toJSONString());
            file.flush();
            System.out.println("Результаты сохранены в output.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject buildAlgorithmSection(MSTResult result) {
        JSONObject algObj = new JSONObject();
        algObj.put("total_cost", result.totalCost);
        algObj.put("operations_count", result.operationsCount);
        algObj.put("execution_time_ms", result.executionTimeMs);

        JSONArray mstEdges = new JSONArray();
        for (Edge e : result.mstEdges) {
            JSONObject edgeObj = new JSONObject();
            edgeObj.put("from", e.from);
            edgeObj.put("to", e.to);
            edgeObj.put("weight", e.weight);
            mstEdges.add(edgeObj);
        }
        algObj.put("mst_edges", mstEdges);

        return algObj;
    }
}
