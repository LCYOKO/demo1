package com.xiaomi.demo.java.net.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/16
 */
public class HttpServer {
    private final int serverPort;
    private final ServerBootstrap bootstrap;

    public HttpServer(int serverPort) {
        this.serverPort = serverPort;
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerHandlerInitializer());
    }

    public void startSync() throws InterruptedException {
        bootstrap.bind(serverPort).channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new HttpServer(9999).startSync();
    }
}
