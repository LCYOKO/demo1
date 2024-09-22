package com.xiaomi.demo.algo.grah;

interface WeightedGraph<Weight extends Number & Comparable> {
    int getNodes();

    int getEdges();

    void addEdge(Edge<Weight> e);

    boolean hasEdge(int v, int w);

    void show();

    Iterable<Edge<Weight>> adj(int v);
}
