package com.assignment4.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple weighted directed graph using 0-based integer vertices.
 */
public class WeightedDirectedGraph {
    private final int n;
    private final List<List<Edge>> adj;
    private final int source;

    public WeightedDirectedGraph(int n, int source) {
        this.n = n;
        this.source = source;
        this.adj = new ArrayList<>();
        for (int i=0;i<n;i++) adj.add(new ArrayList<>());
    }

    public int getN(){ return n; }
    public int getSource(){ return source; }
    public List<List<Edge>> getAdj(){ return adj; }

    public void addEdge(int u,int v,double w){ adj.get(u).add(new Edge(v,w)); }

    public static class Edge {
        public final int to;
        public final double w;
        public Edge(int to,double w){ this.to=to; this.w=w; }
        @Override public String toString(){ return String.format("(%d,%.2f)", to,w); }
    }
}
