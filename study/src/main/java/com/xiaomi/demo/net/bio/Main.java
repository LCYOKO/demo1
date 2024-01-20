package com.xiaomi.demo.net.bio;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        BioEndpoint endpoint = new BioEndpoint(8888);
        endpoint.start();
        downLatch.await();
    }
}
