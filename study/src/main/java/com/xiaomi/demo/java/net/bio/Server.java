package com.xiaomi.demo.java.net.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class Server {
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            process(socket);
        }
    }

    private void process(Socket socket) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream())) {
            log.info("receive msg:{}", getRequest(bufferedInputStream));
            socket.getOutputStream().write(getResponse().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRequest(BufferedInputStream bufferedInputStream) throws IOException {
        byte[] bytes = new byte[1024 * 4];
        int read = bufferedInputStream.read(bytes);
        return new String(bytes, 0, read);
    }

    private String getResponse() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                "{\"name\":\"lisi\"}";
    }

}
