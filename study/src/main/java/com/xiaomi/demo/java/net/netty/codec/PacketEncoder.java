package com.xiaomi.demo.java.net.netty.codec;


import com.xiaomi.demo.java.net.netty.protocol.BasePacket;
import com.xiaomi.demo.java.net.netty.protocol.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
public class PacketEncoder extends MessageToByteEncoder<BasePacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BasePacket basePacket, ByteBuf byteBuf) throws Exception {
        PacketUtils.encode(basePacket, byteBuf);
    }
}
