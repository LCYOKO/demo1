package com.xiaomi.demo.net.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8999));
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

            reader.lines().forEach(System.out::println);
        } catch (Exception e) {
            log.error("read data failed.", e);
        }
        socket.close();
    }
}
