package com.xiaomi.demo.java.net.netty.simple;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
public class CustomerIdleHandler extends IdleStateHandler {
    public CustomerIdleHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        super.channelIdle(ctx, evt);
    }

    @Override
    protected IdleStateEvent newIdleStateEvent(IdleState state, boolean first) {
        return super.newIdleStateEvent(state, first);
    }
}
