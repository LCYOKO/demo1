package com.xiaomi.demo.algo.tree;

public interface Merger<E> {
    E merge(E a, E b);

    Merger<Integer> DEFAULT = Integer::sum;
}
