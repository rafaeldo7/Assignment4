package com.assignment4;

import com.assignment4.common.GraphDatasetLoader;
import com.assignment4.common.WeightedDirectedGraph;
import com.assignment4.graph.scc.SCCFinder;
import com.assignment4.graph.topo.CondensationGraph;
import com.assignment4.graph.topo.TopologicalSort;
import com.assignment4.graph.dagsp.DAGShortestPaths;
import com.assignment4.util.Metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        File dataDir = new File("C:\\Users\\Rafael\\assignment4\\data");

        if (!dataDir.exists() || !dataDir.isDirectory()) {
            System.out.println("Place datasets under ./data/ and re-run.");
            return;
        }

        File[] files = dataDir.listFiles((d, n) -> n.endsWith(".json"));
        if (files == null || files.length == 0) {
            System.out.println("No JSON datasets found in data/");
            for (File f : dataDir.listFiles()) {
                System.out.println("  " + f.getName());
            }
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("results.txt"))) {
            for (File f : files) {
                System.out.println("=== Processing: " + f.getName() + " ===");
                out.println("=== " + f.getName() + " ===");

                WeightedDirectedGraph g = GraphDatasetLoader.load(f.toPath());

                Metrics metrics = new Metrics();
                metrics.startTimer();

                // 1. SCC
                SCCFinder scc = new SCCFinder(g, metrics);
                List<List<Integer>> comps = scc.findSCCs();

                metrics.stopTimer();
                System.out.println("SCC count=" + comps.size() + ", time(ns)=" + metrics.elapsedNanos());
                out.println("SCC count=" + comps.size() + ", time(ns)=" + metrics.elapsedNanos());

                for (int i = 0; i < comps.size(); i++) {
                    System.out.println("Comp " + i + " size=" + comps.get(i).size() + " nodes=" + comps.get(i));
                    out.println("Comp " + i + " size=" + comps.get(i).size() + " nodes=" + comps.get(i));
                }

                // 2. Condensation
                Map<Integer, List<Integer>> cond = CondensationGraph.build(g, scc.getComponentIds());
                System.out.println("Condensation nodes=" + cond.size());
                out.println("Condensation nodes=" + cond.size());

                // 3. Topological sort
                List<Integer> topo = TopologicalSort.sort(cond, metrics);
                System.out.println("Topological order: " + topo);
                out.println("Topological order: " + topo);

                // 4. DAG shortest/longest paths
                var condAdj = CondensationGraph.buildWeightedAdj(g, scc.getComponentIds());
                int src = g.getSource();
                int srcComp = scc.getComponentIds()[src];

                DAGShortestPaths dsp = new DAGShortestPaths(condAdj, metrics);
                var sp = dsp.shortestFrom(srcComp);
                var lp = dsp.longestFrom(srcComp);

                System.out.println("Shortest distances from comp " + srcComp + ":");
                out.println("Shortest distances from comp " + srcComp + ":");
                for (int i = 0; i < sp.dist.length; i++) {
                    System.out.printf("  comp %d -> %.2f\n", i, sp.dist[i]);
                    out.printf("  comp %d -> %.2f\n", i, sp.dist[i]);
                }

                System.out.println("Critical (longest) path value: " + maxValue(lp.dist));
                out.println("Critical (longest) path value: " + maxValue(lp.dist));

                List<Integer> compPath = DAGShortestPaths.reconstructCompPath(lp.prevIndex);
                System.out.println("Component path: " + compPath);
                out.println("Component path: " + compPath);

                // 5. Print metrics
                System.out.println("Metrics: " + metrics);
                out.println("Metrics: " + metrics);

                System.out.println();
                out.println();
            }
        }

        System.out.println("All done. Results written to results.txt");
    }

    private static double maxValue(double[] a) {
        double best = Double.NEGATIVE_INFINITY;
        for (double v : a) {
            if (!Double.isInfinite(v) && v > best) best = v;
        }
        return best;
    }
}
