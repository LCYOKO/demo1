package com.xiaomi.demo.net.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8999);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket);
            new Thread(() -> {
                try (PrintWriter writer = new PrintWriter(socket.getOutputStream());) {
                    writer.println("hello world");
                    writer.flush();
                } catch (Exception exception) {
                    log.error("write data failed.", exception);
                }
            }).start();
        }
    }
}
