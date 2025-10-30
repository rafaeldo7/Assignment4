package com.assignment4.graph.scc;

import java.util.*;

public class KosarajuSCC {
    private final int n;
    private final List<List<Integer>> adj;
    private final List<List<Integer>> revAdj;
    private boolean[] visited;
    private Stack<Integer> order;
    private List<List<Integer>> components;

    public KosarajuSCC(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        this.revAdj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
            revAdj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        revAdj.get(v).add(u);
    }

    public List<List<Integer>> getSCCs() {
        visited = new boolean[n];
        order = new Stack<>();

        for (int i = 0; i < n; i++)
            if (!visited[i]) dfs1(i);

        Arrays.fill(visited, false);
        components = new ArrayList<>();

        while (!order.isEmpty()) {
            int v = order.pop();
            if (!visited[v]) {
                List<Integer> comp = new ArrayList<>();
                dfs2(v, comp);
                components.add(comp);
            }
        }
        return components;
    }

    private void dfs1(int v) {
        visited[v] = true;
        for (int u : adj.get(v))
            if (!visited[u])
                dfs1(u);
        order.push(v);
    }

    private void dfs2(int v, List<Integer> comp) {
        visited[v] = true;
        comp.add(v);
        for (int u : revAdj.get(v))
            if (!visited[u])
                dfs2(u, comp);
    }
}
