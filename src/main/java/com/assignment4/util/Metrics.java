package com.assignment4.util;

public class Metrics {
    private long start=0,end=0;
    public long dfsVisits=0, edgesVisited=0, kahnPushes=0, kahnPops=0, relaxations=0, dagEdgeChecks=0;

    public void startTimer(){ start=System.nanoTime(); }
    public void stopTimer(){ end=System.nanoTime(); }
    public long elapsedNanos(){ return end-start; }

    @Override
    public String toString(){
        return String.format("time(ns)=%d, dfs=%d, edges=%d, pushes=%d, pops=%d, relax=%d, checks=%d",
                elapsedNanos(), dfsVisits, edgesVisited, kahnPushes, kahnPops, relaxations, dagEdgeChecks);
    }
}
