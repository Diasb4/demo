package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject input = (JSONObject) parser.parse(new FileReader("input.json"));
            JSONArray graphs = (JSONArray) input.get("graphs");

            JSONObject outputRoot = new JSONObject();
            JSONArray resultsArray = new JSONArray();

            for (Object g : graphs) {
                JSONObject graphObj = (JSONObject) g;
                long id = (long) graphObj.get("id");

                JSONArray nodeArray = (JSONArray) graphObj.get("nodes");
                List<String> nodes = new ArrayList<>();
                for (Object n : nodeArray)
                    nodes.add((String) n);

                JSONArray edgeArray = (JSONArray) graphObj.get("edges");
                List<Edge> edges = new ArrayList<>();
                for (Object e : edgeArray) {
                    JSONObject edgeObj = (JSONObject) e;
                    String from = (String) edgeObj.get("from");
                    String to = (String) edgeObj.get("to");
                    long weight = (long) edgeObj.get("weight");
                    edges.add(new Edge(from, to, (int) weight));
                }

                Graph graph = new Graph(nodes, edges);

                MSTResult prim = PrimMST.run(graph);
                MSTResult kruskal = KruskalMST.run(graph);

                JSONObject graphResult = new JSONObject();

                // Сначала graph_id
                graphResult.put("graph_id", id);

                // Потом input_stats
                JSONObject inputStats = new JSONObject();
                inputStats.put("vertices", graph.vertices.size());
                inputStats.put("edges", graph.edges.size());
                graphResult.put("input_stats", inputStats);

                // Потом prim
                JSONObject primJson = mstToJson("prim", prim);
                graphResult.put("prim", primJson);

                // В конце kruskal
                JSONObject kruskalJson = mstToJson("kruskal", kruskal);
                graphResult.put("kruskal", kruskalJson);

                resultsArray.add(graphResult);
            }

            outputRoot.put("results", resultsArray);

            try (FileWriter file = new FileWriter("output.json")) {
                file.write(outputRoot.toJSONString());
                file.flush();
                System.out.println("JSON сохранён в output.json");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject mstToJson(String algorithm, MSTResult result) {
        JSONObject algoJson = new JSONObject();

        JSONArray edgesArray = new JSONArray();
        for (Edge e : result.mstEdges) {
            JSONObject edgeJson = new JSONObject();
            edgeJson.put("from", e.from);
            edgeJson.put("to", e.to);
            edgeJson.put("weight", e.weight);
            edgesArray.add(edgeJson);
        }

        algoJson.put("mst_edges", edgesArray);
        algoJson.put("total_cost", result.totalCost);
        algoJson.put("execution_time_ms", result.executionTimeMs);
        algoJson.put("operations_count", result.operationsCount);

        return algoJson;
    }
}