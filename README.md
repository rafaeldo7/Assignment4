# Assignment 4 - SCC, Topological Sort, DAG Shortest/Longest Paths

This Maven project implements:
- SCC detection (Kosaraju)
- Condensation graph construction
- Topological ordering (Kahn)
- Single-source shortest paths and longest path on DAG (DP over topo order)
- Metrics and timing
- Batch processing of all JSON datasets in `/data`

Build & run:
```
mvn clean compile exec:java -Dexec.mainClass=com.assignment4.Main
```
Results: console output and `results.txt` created in project root.

Datasets are included in `/data` (9 files).

