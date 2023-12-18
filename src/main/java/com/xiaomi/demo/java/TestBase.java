package com.xiaomi.demo.java;

import lombok.Data;

import java.util.List;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
public class TestBase {
    public void test(List<? extends Base> list) {
        Base base = list.get(0);
    }

    public void test1(List<? super Base> list) {
        list.add(new Base());
        list.add(new People());
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
