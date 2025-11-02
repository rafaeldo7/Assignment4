package com.assignment4.graph.scc.src.main.java.com.assignment4.graph.topo;
import java.util.*;

public class TopologicalSort {
    public static List<Integer> sort(List<List<Integer>> dag) {
        int n = dag.size();
        int[] indeg = new int[n];
        for (List<Integer> edges : dag)
            for (int v : edges) indeg[v]++;

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : dag.get(u)) {
                indeg[v]--;
                if (indeg[v] == 0) q.add(v);
            }
        }
        return order;
    }
}
