package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();

    public void makeSet(List<String> vertices) {
        for (String v : vertices)
            parent.put(v, v);
    }

    public String find(String v) {
        if (!parent.get(v).equals(v)) {
            parent.put(v, find(parent.get(v)));
        }
        return parent.get(v);
    }

    public void union(String v1, String v2) {
        String root1 = find(v1);
        String root2 = find(v2);
        if (!root1.equals(root2))
            parent.put(root1, root2);
    }
}