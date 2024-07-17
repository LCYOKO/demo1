package com.xiaomi.demo.java.net.netty.protobuf;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
public class ClientChannelHandlerInit extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
//        ChannelPipeline ph = ch.pipeline();
//        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
//        ph.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
//        //传输的协议 Protobuf
//        ph.addLast(new ProtobufVarint32FrameDecoder());
//        ph.addLast(new ProtobufDecoder());
//        ph.addLast(new ProtobufVarint32LengthFieldPrepender());
//        ph.addLast(new ProtobufEncoder());
//        //业务逻辑实现类
//        ph.addLast("nettyClientHandler",new NettyClientHandler());
    }
}
