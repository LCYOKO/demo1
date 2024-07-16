package com.xiaomi.demo.java.net.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
@Slf4j
public class Client {
    private final String remoteHost;
    private final int remotePort;
    private final Bootstrap bootstrap;

    public Client(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        EventLoopGroup group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline
                        pipeline.addLast(new ClientHandler()); //客户端处理类

                    }
                });
    }

    public static void main(String[] args) throws InterruptedException {
        startClient();
    }

    public void startSync() {

    }

    private static void startClient() throws InterruptedException {
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect("localhost", 8999);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture arg0) throws Exception {
                if (future.isSuccess()) {
                    ("连接服务器成功");

                } else {
                    System.out.println("连接服务器失败");

                    group.shutdownGracefully(); //关闭线程组
                }
            }
        });
        future.channel().closeFuture().sync();
    }

    private static class ClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        }
    }
}
