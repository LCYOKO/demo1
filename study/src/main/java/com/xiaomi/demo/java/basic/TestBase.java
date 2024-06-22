package com.xiaomi.demo.java.basic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
@Slf4j
public class TestBase {
    public void test1(List<? extends Base> list) {
        Base base = list.get(0);
    }

    public void test2(List<? super Base> list) {
        list.add(new Base());
        list.add(new People());
    }


    @Test
    public void test3() {
        HashMap<Integer, Integer> map = new HashMap<>();
        //如果不存在则放入
        map.putIfAbsent(1, 1);
        map.computeIfAbsent(1, integer -> integer * 2);
        map.computeIfPresent(1, (key, oldValue) -> null);
    }

    @Test
    public void test4() {
        //2把锁
        new ArrayBlockingQueue<Integer>(100);
        //只有1把锁
        new LinkedBlockingDeque<>(100);
    }

    @Test
    public void test5() {
        Map<Integer, String> map = Map.of(1, "2");
        log.info("map:{}", map);
    }

    static class Item<T extends Base> {

    }

    static class People extends Base {

    }

    @Data
    static
    class Base {
        private String data;
    }
}
