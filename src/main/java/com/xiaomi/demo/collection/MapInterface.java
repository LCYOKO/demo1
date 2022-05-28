package com.xiaomi.demo.collection;

/**
 * @author l
 * @create 2020-08-20-11:56
 */
public interface MapInterface <K,V> {
  public void add(K key, V value);
  public int size();
  public boolean contains(K key);
  public V get(K key);
}
