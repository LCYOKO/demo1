package com.xiaomi.demo.algo;

import org.junit.Test;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/22
 */
public class DataStructTest {

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
    public void testMap(){

    }

    @Test
    public void testCopyList(){

    }
}
