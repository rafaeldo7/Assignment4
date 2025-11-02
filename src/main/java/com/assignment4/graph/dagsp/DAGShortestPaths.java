package com.assignment4.graph.scc.src.main.java.com.assignment4.graph.dagsp;
import java.util.*;

public class DAGShortestPaths {
    private final int n;
    private final List<List<Edge>> adj;
    private double[] dist;
    private int[] parent;

    public static class Edge {
        public int v;
        public double w;
        public Edge(int v, double w) {
            this.v = v;
            this.w = w;
        }
    }

    public DAGShortestPaths(List<List<Edge>> adj) {
        this.adj = adj;
        this.n = adj.size();
    }

    public void computeShortest(int src, List<Integer> topoOrder) {
        dist = new double[n];
        parent = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        for (int u : topoOrder)
            for (Edge e : adj.get(u))
                if (dist[u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[u] + e.w;
                    parent[e.v] = u;
                }
    }

    public void computeLongest(int src, List<Integer> topoOrder) {
        dist = new double[n];
        parent = new int[n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        for (int u : topoOrder)
            for (Edge e : adj.get(u))
                if (dist[u] + e.w > dist[e.v]) {
                    dist[e.v] = dist[u] + e.w;
                    parent[e.v] = u;
                }
    }

    public List<Integer> getPathTo(int target) {
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) path.add(v);
        Collections.reverse(path);
        return path;
    }

    public double[] getDistances() {
        return dist;
    }
}
