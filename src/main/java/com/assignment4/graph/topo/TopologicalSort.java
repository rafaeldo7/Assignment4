package com.assignment4.graph.topo;

import java.util.*;

public class TopologicalSort {
    private final int n;
    private final List<List<Integer>> adj;

    public TopologicalSort(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    public List<Integer> kahnSort() {
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (int v : adj.get(u))
                indeg[v]++;

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0)
                q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int v = q.poll();
            order.add(v);
            for (int u : adj.get(v))
                if (--indeg[u] == 0)
                    q.add(u);
        }
        return order;
    }
}
