package com.xiaomi.demo.java.net.netty.mqtt.protocol;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPublishMessageStoreService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuchiyun
 */
@Slf4j
public class PubAck {

    private final GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService;

    public PubAck(GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService) {
        this.grozaDupPublishMessageStoreService = grozaDupPublishMessageStoreService;
    }

    public void processPubAck(Channel channel, MqttMessageIdVariableHeader variableHeader) {
        int messageId = variableHeader.messageId();
        log.info("PUBACK - clientId: {}, messageId: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), messageId);
        grozaDupPublishMessageStoreService.remove((String) channel.attr(AttributeKey.valueOf("clientId")).get(), messageId);
    }
}
