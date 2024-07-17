package com.xiaomi.demo.java.net.netty.mqtt.protocol;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.*;
import com.xiaomi.demo.java.net.netty.mqtt.common.session.GrozaSessionStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.GrozaSubscribeStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.SubscribeStore;
import com.xiaomi.demo.java.net.netty.mqtt.internal.InternalMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author：liuchiyun
 */
@Slf4j
public class Publish {

    private final GrozaSessionStoreService grozaSessionStoreService;

    private final GrozaSubscribeStoreService grozaSubscribeStoreService;

    private final GrozaMessageIdService grozaMessageIdService;

    private final GrozaRetainMessageStoreService grozaRetainMessageStoreService;

    private final GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService;

    private final GrozaKafkaService grozaKafkaService;


    public Publish(GrozaSessionStoreService grozaSessionStoreService,
                   GrozaSubscribeStoreService grozaSubscribeStoreService,
                   GrozaMessageIdService grozaMessageIdService,
                   GrozaRetainMessageStoreService grozaRetainMessageStoreService,
                   GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService,
                   GrozaKafkaService grozaKafkaService) {
        this.grozaSessionStoreService = grozaSessionStoreService;
        this.grozaSubscribeStoreService = grozaSubscribeStoreService;
        this.grozaMessageIdService = grozaMessageIdService;
        this.grozaRetainMessageStoreService = grozaRetainMessageStoreService;
        this.grozaDupPublishMessageStoreService = grozaDupPublishMessageStoreService;
        this.grozaKafkaService = grozaKafkaService;
    }

    public void processPublish(Channel channel, MqttPublishMessage msg) {
        String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
        byte[] messageBytes = new byte[msg.payload().readableBytes()];
        msg.payload().getBytes(msg.payload().readerIndex(), messageBytes);
        InternalMessage internalMessage = InternalMessage.builder()
                .topic(msg.variableHeader().topicName())
                .mqttQoS(msg.fixedHeader().qosLevel().value())
                .messageBytes(messageBytes)
                .dup(false)
                .retain(false)
                .clientId(clientId)
                .build();
        switch (msg.fixedHeader().qosLevel()) {
            case AT_MOST_ONCE:
                grozaKafkaService.send(internalMessage);
                this.sendPublishMessage(msg.variableHeader().topicName(), msg.fixedHeader().qosLevel(), messageBytes, false, false);
                break;
            case AT_LEAST_ONCE:
                grozaKafkaService.send(internalMessage);
                this.sendPublishMessage(msg.variableHeader().topicName(), msg.fixedHeader().qosLevel(), messageBytes, false, false);
                this.sendPubAckMessage(channel, msg.variableHeader().packetId());
                break;
            case EXACTLY_ONCE:
                grozaKafkaService.send(internalMessage);
                this.sendPublishMessage(msg.variableHeader().topicName(), msg.fixedHeader().qosLevel(), messageBytes, false, false);
                this.sendPubRecMessage(channel, msg.variableHeader().packetId());
                break;
            default:
                throw new IllegalArgumentException("Unsupported QoS: " + msg.fixedHeader().qosLevel());

        }
        // retain=1, 保留消息
        if (msg.fixedHeader().isRetain()) {
            if (messageBytes.length == 0) {
                grozaRetainMessageStoreService.remove(msg.variableHeader().topicName());
            } else {
                RetainMessageStore retainMessageStore = RetainMessageStore
                        .builder()
                        .topic(msg.variableHeader().topicName())
                        .mqttQoS(msg.fixedHeader().qosLevel().value())
                        .messageBytes(messageBytes)
                        .build();
                grozaRetainMessageStoreService.put(msg.variableHeader().topicName(), retainMessageStore);
            }
        }
    }

    private void sendPublishMessage(String topic, MqttQoS mqttQoS, byte[] messageBytes, boolean retain, boolean dup) {
        List<SubscribeStore> subscribeStores = grozaSubscribeStoreService.search(topic);
        for (SubscribeStore subscribeStore : subscribeStores) {
            if (grozaSessionStoreService.containsKey(subscribeStore.getClientId())) {
                // 订阅者收到MQTT消息的QoS级别, 最终取决于发布消息的QoS和主题订阅的QoS
                MqttQoS respQoS = mqttQoS.value() > subscribeStore.getMqttQoS() ? MqttQoS.valueOf(subscribeStore.getMqttQoS()) : mqttQoS;
                if (respQoS == MqttQoS.AT_MOST_ONCE) {
                    MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                            new MqttFixedHeader(MqttMessageType.PUBLISH, dup, respQoS, retain, 0),
                            new MqttPublishVariableHeader(topic, 0),
                            Unpooled.buffer().writeBytes(messageBytes));
                    log.info("PUBLISH - clientId: {}, topic: {}, Qos: {}", subscribeStore.getClientId(), topic, respQoS.value());
                    grozaSessionStoreService.get(subscribeStore.getClientId()).getChannel().writeAndFlush(publishMessage);
                }
                if (respQoS == MqttQoS.AT_LEAST_ONCE) {
                    int messageId = grozaMessageIdService.getNextMessageId();
                    MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                            new MqttFixedHeader(MqttMessageType.PUBLISH, dup, respQoS, retain, 0),
                            new MqttPublishVariableHeader(topic, messageId), Unpooled.buffer().writeBytes(messageBytes));
                    log.info("PUBLISH - clientId: {}, topic: {}, Qos: {}, messageId: {}", subscribeStore.getClientId(), topic, respQoS.value(), messageId);
                    DupPublishMessageStore dupPublishMessageStore = DupPublishMessageStore
                            .builder()
                            .clientId(subscribeStore.getClientId())
                            .topic(topic)
                            .mqttQoS(respQoS.value())
                            .messageBytes(messageBytes)
                            .messageId(messageId)
                            .build();
                    grozaDupPublishMessageStoreService.put(subscribeStore.getClientId(), dupPublishMessageStore);
                    grozaSessionStoreService.get(subscribeStore.getClientId()).getChannel().writeAndFlush(publishMessage);
                }
                if (respQoS == MqttQoS.EXACTLY_ONCE) {
                    int messageId = grozaMessageIdService.getNextMessageId() + 1;
                    MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                            new MqttFixedHeader(MqttMessageType.PUBLISH, dup, respQoS, retain, 0),
                            new MqttPublishVariableHeader(topic, messageId), Unpooled.buffer().writeBytes(messageBytes));
                    log.info("PUBLISH - clientId: {}, topic: {}, Qos: {}, messageId: {}", subscribeStore.getClientId(), topic, respQoS.value(), messageId);
                    DupPublishMessageStore dupPublishMessageStore = DupPublishMessageStore
                            .builder()
                            .clientId(subscribeStore.getClientId())
                            .topic(topic)
                            .mqttQoS(respQoS.value())
                            .messageBytes(messageBytes)
                            .messageId(messageId)
                            .build();
                    grozaDupPublishMessageStoreService.put(subscribeStore.getClientId(), dupPublishMessageStore);
                    grozaSessionStoreService.get(subscribeStore.getClientId()).getChannel().writeAndFlush(publishMessage);
                }
            }
        }
    }

    private void sendPubAckMessage(Channel channel, int messageId) {
        MqttPubAckMessage pubAckMessage = (MqttPubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId),
                null);
        channel.writeAndFlush(pubAckMessage);
    }

    private void sendPubRecMessage(Channel channel, int messageId) {
        MqttMessage pubRecMessage = MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBREC, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId),
                null);
        channel.writeAndFlush(pubRecMessage);
    }

}
