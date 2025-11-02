package com.assignment4.graph.scc;

import com.assignment4.common.WeightedDirectedGraph;
import com.assignment4.util.Metrics;

import java.util.*;

/**
 * Kosaraju implementation for SCC with metrics
 */
public class SCCFinder {
    private final WeightedDirectedGraph G;
    private final int n;
    private List<List<Integer>> adj;
    private List<List<Integer>> rev;
    private boolean[] visited;
    private Deque<Integer> order;
    private int[] compId;
    private Metrics metrics;

    public SCCFinder(WeightedDirectedGraph G, Metrics metrics) {
        this.G = G;
        this.n = G.getN();
        this.metrics = metrics;
        buildAdj();
    }

    private void buildAdj() {
        adj = new ArrayList<>();
        rev = new ArrayList<>();
        for (int i=0;i<n;i++){ adj.add(new ArrayList<>()); rev.add(new ArrayList<>()); }
        for (int u=0;u<n;u++){
            for (var e : G.getAdj().get(u)) {
                adj.get(u).add(e.to);
                rev.get(e.to).add(u);
            }
        }
    }

    public List<List<Integer>> findSCCs() {
        visited = new boolean[n];
        order = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (!visited[i]) dfs1(i);
        Arrays.fill(visited,false);
        List<List<Integer>> comps = new ArrayList<>();
        compId = new int[n];
        while (!order.isEmpty()) {
            int v = order.pop();
            if (!visited[v]) {
                List<Integer> comp = new ArrayList<>();
                dfs2(v, comp);
                for (int x : comp) compId[x] = comps.size();
                comps.add(comp);
            }
        }
        return comps;
    }

    private void dfs1(int v){
        visited[v]=true;
        metrics.dfsVisits++;
        for (int to: adj.get(v)) {
            metrics.edgesVisited++;
            if (!visited[to]) dfs1(to);
        }
        order.push(v);
    }
    private void dfs2(int v, List<Integer> comp){
        visited[v]=true;
        metrics.dfsVisits++;
        comp.add(v);
        for (int to: rev.get(v)) {
            metrics.edgesVisited++;
            if (!visited[to]) dfs2(to, comp);
        }
    }

    public int[] getComponentIds(){ return compId; }
}