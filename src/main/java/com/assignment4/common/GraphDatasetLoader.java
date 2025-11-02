package com.assignment4.common;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GraphDatasetLoader {
    public static WeightedDirectedGraph load(Path p) throws IOException {
        String s = Files.readString(p);
        Gson g = new Gson();
        JsonObject root = g.fromJson(s, JsonObject.class);
        int n = root.get("n").getAsInt();
        int source = root.has("source") ? root.get("source").getAsInt() : 0;
        WeightedDirectedGraph G = new WeightedDirectedGraph(n, source);
        JsonArray edges = root.getAsJsonArray("edges");
        for (JsonElement je : edges) {
            JsonObject eo = je.getAsJsonObject();
            int u = eo.get("u").getAsInt();
            int v = eo.get("v").getAsInt();
            double w = eo.has("w") ? eo.get("w").getAsDouble() : 1.0;
            G.addEdge(u,v,w);
        }
        return G;
    }
}
