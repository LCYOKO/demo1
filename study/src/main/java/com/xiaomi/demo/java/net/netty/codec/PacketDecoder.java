package com.xiaomi.demo.java.net.netty.codec;

import com.xiaomi.demo.java.net.netty.protocol.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketUtils.decode(byteBuf));
    }
}
