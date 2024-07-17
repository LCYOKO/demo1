package com.xiaomi.demo.java.net.netty.simple;

import com.xiaomi.demo.java.net.netty.codec.PacketDecoder;
import com.xiaomi.demo.java.net.netty.codec.PacketEncoder;
import com.xiaomi.demo.java.net.netty.codec.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
@Slf4j
public class Server {
    private final int port;
    private final ServerBootstrap serverBootstrap;

    public Server(int port) {
        this.port = port;
        this.serverBootstrap = new ServerBootstrap();
        init();
    }

    private void init() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildHandler());
    }


    public void startSync() {
        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            log.info("error", ex);
        }
    }

    private static class ChildHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new Spliter());
            ch.pipeline().addLast(new PacketDecoder());
            ch.pipeline().addLast(new PacketEncoder());
            ch.pipeline().addLast(new LoginRequestHandler());
            ch.pipeline().addLast(new TestOutboundHandler());
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(9999).startSync();
    }

}
