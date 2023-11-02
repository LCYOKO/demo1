package com.xiaomi.demo.collection;

/**
 * @Author: lcy
 * @Date 14:54 2021/3/13
 * <p>
 * -------------------------hashMap1.7和hashMap1.8的区别------------------------------------
 * 1）扰动函数的优化，1.7是多少移位和异或操作，1.8是高16位和第16位进行异或
 * 2）hash冲突的解决优化，1.7是通过拉链法解决冲突，1.8也是通过拉链法解决冲突，但是在链表个数>=8时会转换为红黑树加快检索，<=6时退化链表
 * 3）扩容插入方式不同，1.7是头查并发操作容易导致死锁，1.8是尾插避免死锁
 * 4）扩容时间点也不一样，1.7是先扩容再插入，1.8是先插入再扩容
 * <p>
 * -------------------------------------hashMap1.8-------------------------------------------
 * <p>
 * 1 put方法，先判断当前table是否初始化，未初始化则进行init，再判断key==null，如果等于null直接放到0号位
 * 2 通过hash定位到index，判断头结点是否未null，是的话直接设置头结点
 * 3 如果不为null，判断头结点是否是红黑树结点，是的话直接调用红黑树的添加方法
 * 4 否则按照链表的方式进行put，equals()判断key是否存在，不存在添加链表尾部，否则进行覆盖
 * 5 判断是否需要扩容
 */

public class MyMap<K, V> implements MapInterface<K, V> {

    @Override
    public void add(K key, V value) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }
}


