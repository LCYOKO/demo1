package com.xiaomi.demo.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.util.concurrent.FutureListener;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
           startClient();
    }

    private static void startClient() throws InterruptedException {
        final EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        // 使用NioSocketChannel来作为连接用的channel类
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("正在连接中...");
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast(new RpcEncoder(RpcRequest.class)); //编码request
//                        pipeline.addLast(new RpcDecoder(RpcResponse.class)); //解码response
                        pipeline.addLast(new ClientHandler()); //客户端处理类

                    }
                });
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect("localhost", 8999).sync();

        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture arg0) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功");

                } else {
                    System.out.println("连接服务器失败");
                    future.cause().printStackTrace();
                    group.shutdownGracefully(); //关闭线程组
                }
            }
        });

    }

    private static class ClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Scanner scanner = new Scanner(System.in);
            String str = null;
            while (!"#".equals(str = scanner.nextLine())) {
                System.out.println(str);
                ctx.writeAndFlush(Unpooled.copiedBuffer(str, StandardCharsets.UTF_8));
            }
            super.channelActive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println(123);
            super.userEventTriggered(ctx, evt);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        }
    }
}
