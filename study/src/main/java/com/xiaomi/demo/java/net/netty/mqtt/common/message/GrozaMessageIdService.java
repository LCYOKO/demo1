package com.xiaomi.demo.java.net.netty.mqtt.common.message;

/**
 * @author liuchiyun
 * 分布式生成报文标识符
 */
public interface GrozaMessageIdService {
    /**
     * 获取报文标识符
     */
    int getNextMessageId();
}
