package com.xiaomi.demo.java.net.netty.simple;

import com.xiaomi.demo.java.net.netty.codec.PacketDecoder;
import com.xiaomi.demo.java.net.netty.codec.PacketEncoder;
import com.xiaomi.demo.java.net.netty.codec.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
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
    private final EventLoopGroup worker;

    public Client(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.worker = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        init();
    }

    public void init() {
        bootstrap.group(worker).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });
    }

    public static void main(String[] args) throws InterruptedException {
        new Client("localhost", 9999).startSync();
    }

    public void startSync() throws InterruptedException {
        bootstrap.connect(remoteHost, remotePort).channel().closeFuture().sync();
    }
}
