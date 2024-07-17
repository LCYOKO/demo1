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
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        log.info("request:{}", msg);
        // ctx.channel().writeAndFlush() 会从最后一个outboundhandler开始执行
        // ctx.writeAndFlush 直接从当前handler开始执行
        ctx.channel().writeAndFlush(LoginResponse.success(1L, "success"));
    }
}
