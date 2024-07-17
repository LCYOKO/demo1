package com.xiaomi.demo.java.net.netty.simple;

import com.xiaomi.demo.java.net.netty.protocol.request.LoginRequest;
import com.xiaomi.demo.java.net.netty.protocol.response.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        log.info("response:{}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush( LoginRequest.of("xiaomi", "123456"));
        super.channelActive(ctx);
    }
}
