package com.xiaomi.demo.java.net.bio;

import java.io.IOException;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
public class Main {
    public static void main(String[] args) throws IOException {
          new Server(9999).start();
    }
}
