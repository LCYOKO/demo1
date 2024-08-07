package com.xiaomi.demo.java.net.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/18
 */
@Slf4j
public class Acceptor implements Runnable {

    /**
     * 服务端Channel句柄
     */
    private final ServerSocketChannel serverSocket;

    /**
     * handler线程池句柄
     */
    private final ExecutorService handlerThreadGroup;


    public Acceptor(ServerSocketChannel serverSocket, ExecutorService handlerThreadGroup) {
        this.serverSocket = serverSocket;
        this.handlerThreadGroup = handlerThreadGroup;
    }

    @Override
    public void run() {
        try {
            // 获取客户端连接
            SocketChannel channel = serverSocket.accept();
            if (Objects.nonNull(channel)) {
                // 将客户端连接交由线程池处理
                handlerThreadGroup.execute(new Handler(channel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
