package com.xiaomi.demo.algo.grah;

import java.util.Vector;
//
///**
// * 稀疏图 - 邻接表
// * @author lcy
// */
//public class SparseWeightedGraph<Weight extends Number & Comparable> implements WeightedGraph {
//    /**
//     * 节点数
//     */
//    private final int n;
//    /**
//     * 边数
//     */
//    private int m;
//
//    /**
//     * 是否为有向图
//     */
//    private final boolean directed;
//
//    /**
//     * 图的具体数据
//     */
//    private final Vector<Edge<Weight>>[] g;
//
//    // 构造函数
//    public SparseWeightedGraph(int n, boolean directed) {
//        assert n >= 0;
//        this.n = n;
//        this.m = 0;
//        this.directed = directed;
//        // g初始化为n个空的vector, 表示每一个g[i]都为空, 即没有任和边
//        g = new Vector[n];
//        for (int i = 0; i < n; i++) {
//            g[i] = new Vector<>();
//        }
//    }
//
//    @Override
//    public int getNodes() {
//        return n;
//    }
//
//    @Override
//    public int getEdges() {
//        return m;
//    }
//
//    // 向图中添加一个边, 权值为weight
//    @Override
//    public void addEdge(Edge e) {
//        assert e.getFirstNode() >= 0 && e.getFirstNode() < n;
//        assert e.getSecondNode() >= 0 && e.getSecondNode() < n;
//        // 注意, 由于在邻接表的情况, 查找是否有重边需要遍历整个链表
//        // 我们的程序允许重边的出现
//        g[e.getFirstNode()].add(new Edge<>(e));
//        if (e.getFirstNode() != e.getSecondNode() && !directed) {
//            g[e.getSecondNode()].add(new Edge(e.getSecondNode(), e.getFirstNode(), e.wt()));
//        }
//        m++;
//    }
//
//    // 验证图中是否有从v到w的边
//    @Override
//    public boolean hasEdge(int v, int w) {
//        assert v >= 0 && v < n;
//        assert w >= 0 && w < n;
//
//        for (int i = 0; i < g[v].size(); i++) {
//            if (g[v].elementAt(i).other(v) == w) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // 显示图的信息
//    @Override
//    public void show() {
//        for (int i = 0; i < n; i++) {
//            System.out.print("vertex " + i + ":\t");
//            for (int j = 0; j < g[i].size(); j++) {
//                Edge<Weight> e = g[i].elementAt(j);
//                System.out.print("( to:" + e.other(i) + ",wt:" + e.wt() + ")\t");
//            }
//            System.out.println();
//        }
//    }
//
//    /**
//     * 返回图中一个顶点的所有邻边
//     */
//    @Override
//    public Iterable<Edge<Weight>> adj(int v) {
//        assert v >= 0 && v < n;
//        return g[v];
//    }
//}