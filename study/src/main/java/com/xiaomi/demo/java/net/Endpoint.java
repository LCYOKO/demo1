package com.xiaomi.demo.java.net;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
public interface Endpoint {
    boolean start();

    boolean close();

    boolean isRunning();
}
