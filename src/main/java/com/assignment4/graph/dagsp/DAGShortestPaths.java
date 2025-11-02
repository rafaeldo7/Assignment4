package com.assignment4.graph.dagsp;

import com.assignment4.util.Metrics;

import java.util.*;

public class DAGShortestPaths {
    private final List<List<double[]>> adj;
    private Metrics metrics;

    public DAGShortestPaths(List<List<double[]>> adj, Metrics metrics) {
        this.adj = adj;
        this.metrics = metrics;
    }

    public static class SPResult {
        public final double[] dist;
        public final int[] prevIndex;
        public SPResult(double[] dist, int[] prevIndex) { this.dist = dist; this.prevIndex = prevIndex; }
    }

    public SPResult shortestFrom(int src) {
        int n = adj.size();
        double INF = Double.POSITIVE_INFINITY;
        double[] dist = new double[n]; Arrays.fill(dist, INF);
        int[] prev = new int[n]; Arrays.fill(prev, -1);
        dist[src]=0;
        List<Integer> topo = topoOrder();
        for (int u : topo) {
            if (Double.isInfinite(dist[u])) continue;
            for (double[] e : adj.get(u)) {
                int v = (int)e[0]; double w = e[1];
                metrics.dagEdgeChecks++;
                if (dist[u] + w < dist[v]) { dist[v] = dist[u] + w; prev[v]=u; metrics.relaxations++; }
            }
        }
        return new SPResult(dist, prev);
    }

    public SPResult longestFrom(int src) {
        int n = adj.size();
        double NEG = Double.NEGATIVE_INFINITY;
        double[] best = new double[n]; Arrays.fill(best, NEG);
        int[] prev = new int[n]; Arrays.fill(prev, -1);
        best[src]=0;
        List<Integer> topo = topoOrder();
        for (int u : topo) {
            if (Double.isInfinite(best[u])) continue;
            for (double[] e : adj.get(u)) {
                int v = (int)e[0]; double w = e[1];
                metrics.dagEdgeChecks++;
                if (best[u] + w > best[v]) { best[v] = best[u] + w; prev[v]=u; metrics.relaxations++; }
            }
        }
        return new SPResult(best, prev);
    }

    public static List<Integer> reconstructCompPath(int[] prev) {
        int best = -1;
        for (int i=0;i<prev.length;i++) if (prev[i]!=-1) best = i;
        if (best==-1) return List.of();
        LinkedList<Integer> path = new LinkedList<>();
        for (int cur=best; cur!=-1; cur=prev[cur]) path.addFirst(cur);
        return path;
    }

    private List<Integer> topoOrder() {
        int n = adj.size();
        boolean[] vis = new boolean[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (!vis[i]) dfs(i, vis, st);
        List<Integer> topo = new ArrayList<>();
        while (!st.isEmpty()) topo.add(st.pop());
        return topo;
    }

    private void dfs(int v, boolean[] vis, Deque<Integer> st) {
        vis[v]=true;
        for (double[] e: adj.get(v)) {
            int to=(int)e[0];
            if (!vis[to]) dfs(to, vis, st);
        }
        st.push(v);
    }
}
