package com.xiaomi.demo.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class Server {
    public static void main(String[] args) throws IOException {
        startServer();
    }

    private static void startServer() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
           ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(boss, worker)
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,1024)
                   .childHandler(new ChildHandler());
            ChannelFuture future = serverBootstrap.bind(8999).sync();
            future.channel().closeFuture().sync();
//               ServerBootstrap
        } catch (Exception e) {


        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static class ChildHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new MyChannelHandler());
//            System.out.println(ch);
        }
    }

    private static class MyChannelHandler extends SimpleChannelInboundHandler<ByteBuffer>{

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
             System.out.println(((ByteBuf)msg).toString(StandardCharsets.UTF_8));
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
          System.out.println(new String(msg.array(), StandardCharsets.UTF_8));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }
    }

}
