package com.assignment4.graph.scc.src.main.java.com.assignment4.graph.scc;
import java.util.*;

public class SCCFinder {
    private final int n;
    private final List<List<Integer>> adj;
    private boolean[] visited;
    private Deque<Integer> stack;
    private int[] componentId;
    private List<List<Integer>> components;

    public SCCFinder(List<List<Integer>> adj) {
        this.n = adj.size();
        this.adj = adj;
    }

    public List<List<Integer>> findSCCs() {
        visited = new boolean[n];
        stack = new ArrayDeque<>();

        // 1️⃣ DFS по исходному графу
        for (int i = 0; i < n; i++) {
            if (!visited[i]) dfs1(i);
        }

        // 2️⃣ Транспонирование графа
        List<List<Integer>> rev = new ArrayList<>();
        for (int i = 0; i < n; i++) rev.add(new ArrayList<>());
        for (int u = 0; u < n; u++)
            for (int v : adj.get(u))
                rev.get(v).add(u);

        // 3️⃣ DFS по транспонированному графу (по убыванию времени выхода)
        Arrays.fill(visited, false);
        components = new ArrayList<>();
        componentId = new int[n];
        int id = 0;

        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> comp = new ArrayList<>();
                dfs2(v, rev, comp, id);
                components.add(comp);
                id++;
            }
        }
        return components;
    }

    private void dfs1(int v) {
        visited[v] = true;
        for (int u : adj.get(v))
            if (!visited[u]) dfs1(u);
        stack.push(v);
    }

    private void dfs2(int v, List<List<Integer>> rev, List<Integer> comp, int id) {
        visited[v] = true;
        comp.add(v);
        componentId[v] = id;
        for (int u : rev.get(v))
            if (!visited[u]) dfs2(u, rev, comp, id);
    }

    public int[] getComponentIds() {
        return componentId;
    }
}
