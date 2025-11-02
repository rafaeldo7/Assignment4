package com.assignment4.graph.topo;

import com.assignment4.common.WeightedDirectedGraph;

import java.util.*;

public class CondensationGraph {
    public static Map<Integer, List<Integer>> build(WeightedDirectedGraph G, int[] compId) {
        int m = Arrays.stream(compId).max().orElse(-1)+1;
        Map<Integer, Set<Integer>> tmp = new HashMap<>();
        for (int i=0;i<m;i++) tmp.put(i, new HashSet<>());
        for (int u=0; u<G.getN(); u++) {
            for (var e : G.getAdj().get(u)) {
                int v = e.to;
                int cu = compId[u], cv = compId[v];
                if (cu!=cv) tmp.get(cu).add(cv);
            }
        }
        Map<Integer,List<Integer>> res = new HashMap<>();
        for (int i=0;i<m;i++) res.put(i, new ArrayList<>(tmp.get(i)));
        return res;
    }

    public static List<List<double[]>> buildWeightedAdj(WeightedDirectedGraph G, int[] compId) {
        int m = Arrays.stream(compId).max().orElse(-1)+1;
        List<Map<Integer, Double>> maps = new ArrayList<>();
        for (int i=0;i<m;i++) maps.add(new HashMap<>());
        for (int u=0; u<G.getN(); u++) {
            for (var e : G.getAdj().get(u)) {
                int v = e.to; int cu = compId[u], cv = compId[v];
                if (cu==cv) continue;
                maps.get(cu).merge(cv, e.w, Math::min);
            }
        }
        List<List<double[]>> adj = new ArrayList<>();
        for (int i=0;i<m;i++) {
            List<double[]> list = new ArrayList<>();
            for (var en : maps.get(i).entrySet()) list.add(new double[]{en.getKey(), en.getValue()});
            adj.add(list);
        }
        return adj;
    }
}