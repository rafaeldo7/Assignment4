package com.assignment4.graph.scc.src.main.java.com.assignment4.graph.topo;
import java.util.*;

public class CondensationGraph {
    public static List<List<Integer>> build(int n, int[] compId, List<List<Integer>> adj) {
        int compCount = Arrays.stream(compId).max().orElse(0) + 1;
        List<Set<Integer>> temp = new ArrayList<>();
        for (int i = 0; i < compCount; i++) temp.add(new HashSet<>());

        for (int u = 0; u < n; u++)
            for (int v : adj.get(u))
                if (compId[u] != compId[v])
                    temp.get(compId[u]).add(compId[v]);

        List<List<Integer>> dag = new ArrayList<>();
        for (Set<Integer> s : temp)
            dag.add(new ArrayList<>(s));
        return dag;
    }
}