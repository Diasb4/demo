package com.example;

import com.example.Algorithms.KruskalMST;
import com.example.Algorithms.PrimMST;
import com.example.Graph.Edge;
import com.example.Graph.Graph;
import com.example.Report.MSTResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Чтение входного JSON
            JsonNode input = mapper.readTree(new File("input.json"));
            ArrayNode graphs = (ArrayNode) input.get("graphs");

            ObjectNode outputRoot = mapper.createObjectNode();
            ArrayNode resultsArray = mapper.createArrayNode();

            for (JsonNode g : graphs) {
                long id = g.get("graph_id").asLong();

                // Узлы
                List<String> nodes = new ArrayList<>();
                for (JsonNode n : g.get("vertices")) {
                    nodes.add(n.asText());
                }

                // Рёбра
                List<Edge> edges = new ArrayList<>();
                for (JsonNode e : g.get("edges")) {
                    String from = e.get("from").asText();
                    String to = e.get("to").asText();
                    int weight = e.get("weight").asInt();
                    edges.add(new Edge(from, to, weight));
                }

                Graph graph = new Graph(id, nodes, edges);

                MSTResult prim = PrimMST.run(graph);
                MSTResult kruskal = KruskalMST.run(graph);

                // Формируем JSON для одного графа
                ObjectNode graphResult = mapper.createObjectNode();
                graphResult.put("graph_id", id);

                ObjectNode inputStats = mapper.createObjectNode();
                inputStats.put("vertices", graph.vertices.size());
                inputStats.put("edges", graph.edges.size());
                graphResult.set("input_stats", inputStats);

                graphResult.set("prim", mstToJson(mapper, prim, id));
                graphResult.set("kruskal", mstToJson(mapper, kruskal, id));

                resultsArray.add(graphResult);
            }

            outputRoot.set("results", resultsArray);

            // Сохраняем JSON красиво
            try (FileWriter file = new FileWriter("output.json")) {
                file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputRoot));
                file.flush();
                System.out.println("JSON сохранён в output.json");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ObjectNode mstToJson(ObjectMapper mapper, MSTResult result, long graphId) {
        ObjectNode algoJson = mapper.createObjectNode();

        ArrayNode edgesArray = mapper.createArrayNode();
        for (Edge e : result.mstEdges) {
            ObjectNode edgeJson = mapper.createObjectNode();
            edgeJson.put("from", e.from);
            edgeJson.put("to", e.to);
            edgeJson.put("weight", e.weight);
            edgesArray.add(edgeJson);
        }

        algoJson.put("graph_id", graphId);
        algoJson.set("mst_edges", edgesArray);
        algoJson.put("total_cost", result.totalCost);
        algoJson.put("execution_time_ms", result.executionTimeMs);
        algoJson.put("operations_count", result.operationsCount);

        return algoJson;
    }
}
