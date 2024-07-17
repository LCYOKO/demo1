package com.xiaomi.demo.java.net.netty.mqtt.protocol;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.DupPubRelMessageStore;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPubRelMessageStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPublishMessageStoreService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author：liuchiyun
 */
@Slf4j
public class PubRec {

    private final GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService;

    private final GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService;

    public PubRec(GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService,
                  GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService) {
        this.grozaDupPublishMessageStoreService = grozaDupPublishMessageStoreService;
        this.grozaDupPubRelMessageStoreService = grozaDupPubRelMessageStoreService;
    }

    public void processPubRec(Channel channel, MqttMessageIdVariableHeader variableHeader) {
        MqttMessage pubRelMessage = MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBREL, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(variableHeader.messageId()),
                null);
        log.info("PUBREC - clientId: {}, messageId: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), variableHeader.messageId());
        grozaDupPublishMessageStoreService.remove((String) channel.attr(AttributeKey.valueOf("clientId")).get(), variableHeader.messageId());
        DupPubRelMessageStore dupPubRelMessageStore = DupPubRelMessageStore
                .builder()
                .clientId((String) channel.attr(AttributeKey.valueOf("clientId")).get())
                .messageId(variableHeader.messageId())
                .build();
        grozaDupPubRelMessageStoreService.put((String) channel.attr(AttributeKey.valueOf("clientId")).get(), dupPubRelMessageStore);
        channel.writeAndFlush(pubRelMessage);
    }
}
