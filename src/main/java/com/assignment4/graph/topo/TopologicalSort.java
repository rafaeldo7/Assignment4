package com.assignment4.graph.topo;

import com.assignment4.util.Metrics;

import java.util.*;

public class TopologicalSort {
    public static List<Integer> sort(Map<Integer,List<Integer>> dag, Metrics metrics) {
        int n = dag.size();
        int[] indeg = new int[n];
        for (var entry : dag.entrySet()) {
            for (int v : entry.getValue()) indeg[v]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (indeg[i]==0) {
            q.add(i);
            metrics.kahnPushes++;
        }
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.remove();
            metrics.kahnPops++;
            order.add(u);
            for (int v : dag.get(u)) {
                indeg[v]--;
                if (indeg[v]==0) {
                    q.add(v);
                    metrics.kahnPushes++;
                }
            }
        }
        return order;
    }
}