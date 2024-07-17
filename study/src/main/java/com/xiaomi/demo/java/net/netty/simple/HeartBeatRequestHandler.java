package com.xiaomi.demo.java.net.netty.simple;

import com.xiaomi.demo.java.net.netty.protocol.request.HeartBeatRequest;
import com.xiaomi.demo.java.net.netty.protocol.response.HeartBeatResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.channels.spi.SelectorProvider;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequest> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequest requestPacket) {
        ctx.writeAndFlush(new HeartBeatResponse());
    }
}
