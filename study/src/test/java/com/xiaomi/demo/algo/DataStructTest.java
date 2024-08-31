package com.xiaomi.demo.algo;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/22
 */
public class DataStructTest {

    @Test
    public void test1() {
    }

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
            return o1 - o2;
        });
        queue.add(1);
        queue.add(2);
        System.out.println(queue);
    }

    @Test
    public void testMap() {
        //JDK 1.7 hashMap 头插法会有死循环
        //  a b c
        // 线程A next = a .next
        // 线程B  c->b->a
        // 线程A  a->c->b->a    b->a->c->b->a

        //1.8
        // a b c a b c
    }

    @Test
    public void testCopyList() {
//         new ArrayList<>()
    }

    @Test
    public void testBlockQueue() {
//        new LinkedBlockingDeque<>();
//        new ArrayBlockingQueue<>();
    }
}
