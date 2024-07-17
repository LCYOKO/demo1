package com.xiaomi.demo.java.net.netty.mqtt.protocol;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPubRelMessageStoreService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuchiyun
 */
@Slf4j
public class PubComp {

    private final GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService;

    public PubComp(GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService) {
        this.grozaDupPubRelMessageStoreService = grozaDupPubRelMessageStoreService;
    }

    public void processPubComp(Channel channel, MqttMessageIdVariableHeader variableHeader) {
        int messageId = variableHeader.messageId();
        log.info("PUBCOMP - clientId: {}, messageId: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), messageId);
        grozaDupPubRelMessageStoreService.remove((String) channel.attr(AttributeKey.valueOf("clientId")).get(), variableHeader.messageId());
    }
}
