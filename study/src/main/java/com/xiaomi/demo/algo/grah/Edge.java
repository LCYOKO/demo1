package com.xiaomi.demo.algo.grah;

public class Edge<Weight extends Number & Comparable> implements Comparable<Edge<Weight>> {

    /**
     * 边的两个端点
     */
    private final int a;
    private final int b;
    /**
     * 边的权值
     */
    private final Weight weight;

    public Edge(int a, int b, Weight weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public Edge(Edge<Weight> e) {
        this.a = e.a;
        this.b = e.b;
        this.weight = e.weight;
    }


    public int getFirstNode() {
        return a;
    }

    public int getSecondNode() {
        return b;
    }

    public Weight getWeight() {
        return weight;
    }

    /**
     * 给定一个顶点, 返回另一个顶点
     */
    public int other(int x) {
        assert x == a || x == b;
        return x == a ? b : a;
    }

    @Override
    public String toString() {
        return "" + a + "-" + b + ": " + weight;
    }

    @Override
    public int compareTo(Edge<Weight> that) {
        if (weight.compareTo(that.getWeight()) < 0) {
            return -1;
        }
        if (weight.compareTo(that.getWeight()) > 0) {
            return +1;
        }
        return 0;
    }
}
