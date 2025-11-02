package com.assignment4;

import com.assignment4.common.WeightedDirectedGraph;
import com.assignment4.graph.scc.SCCFinder;
import com.assignment4.graph.topo.CondensationGraph;
import com.assignment4.graph.topo.TopologicalSort;
import com.assignment4.graph.dagsp.DAGShortestPaths;
import com.assignment4.util.Metrics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTests {

    @Test
    public void demoGraphAlgorithms() {
        System.out.println("=== SCC + Topological Sort Demo ===");
        WeightedDirectedGraph g = new WeightedDirectedGraph(6, 0);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,0,1); // cycle
        g.addEdge(3,4,1); g.addEdge(4,5,1); // chain

        Metrics m = new Metrics();
        SCCFinder scc = new SCCFinder(g, m);
        List<List<Integer>> comps = scc.findSCCs();
        System.out.println("SCCs:");
        for (List<Integer> c : comps) {
            System.out.println(c);
        }

        Map<Integer, List<Integer>> cond = CondensationGraph.build(g, scc.getComponentIds());
        List<Integer> topo = TopologicalSort.sort(cond, m);
        System.out.println("Topological order of condensation graph: " + topo);

        System.out.println("\n=== DAG Shortest/Longest Paths Demo ===");
        WeightedDirectedGraph dag = new WeightedDirectedGraph(4,0);
        dag.addEdge(0,1,2);
        dag.addEdge(0,2,3);
        dag.addEdge(1,3,4);
        dag.addEdge(2,3,1);

        SCCFinder dagSCC = new SCCFinder(dag, m);
        dagSCC.findSCCs(); // DAG, so each node is its own SCC

        var condAdj = CondensationGraph.buildWeightedAdj(dag, dagSCC.getComponentIds());
        DAGShortestPaths dsp = new DAGShortestPaths(condAdj, m);

        var sp = dsp.shortestFrom(0);
        var lp = dsp.longestFrom(0);

        System.out.println("Shortest distance to each node:");
        for (int i = 0; i < sp.dist.length; i++) {
            System.out.println("Node " + i + ": " + sp.dist[i]);
        }

        System.out.println("Longest distance to each node:");
        for (int i = 0; i < lp.dist.length; i++) {
            System.out.println("Node " + i + ": " + lp.dist[i]);
        }

        System.out.println("One shortest path to node 3: " + reconstructPath(3, sp.prev));
        System.out.println("One longest path to node 3: " + reconstructPath(3, lp.prev));
    }

    private List<Integer> reconstructPath(int target, int[] prev) {
        List<Integer> path = new ArrayList<>();
        for (int at = target; at != -1; at = prev[at]) {
            path.add(0, at); // вставляем в начало списка
        }
        if (path.size() == 1 && path.get(0) != target) return List.of(); // путь не найден
        return path;
    }
}
