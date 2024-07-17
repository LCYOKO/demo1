package com.xiaomi.demo.java.net.netty.simple;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
@Slf4j
public class TestOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("msg");
        super.write(ctx, msg, promise);
    }
}
