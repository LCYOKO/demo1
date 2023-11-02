package com.xiaomi.demo.collection;

/**
 * @author l
 * @create 2020-08-20-11:56
 */
public interface MapInterface<K, V> {
    void add(K key, V value);

    int size();

    boolean contains(K key);

    V get(K key);
}
