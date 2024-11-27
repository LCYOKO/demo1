package com.xiaomi.demo;

/**
 * @Author: liuchiyun
 * @Date: 2024/11/27
 */
public class RunTimeTest {

    public static void main(String[] args) {
        System.out.println("before");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("shutdown")));
    }
}
