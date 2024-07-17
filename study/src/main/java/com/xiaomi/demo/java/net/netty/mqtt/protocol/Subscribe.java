package com.xiaomi.demo.java.net.netty.mqtt.protocol;

import cn.hutool.core.util.StrUtil;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaMessageIdService;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaRetainMessageStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.RetainMessageStore;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.GrozaSubscribeStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.SubscribeStore;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuchiyun
 */
@Slf4j
public class Subscribe {

    private final GrozaSubscribeStoreService grozaSubscribeStoreService;

    private final GrozaMessageIdService grozaMessageIdService;

    private final GrozaRetainMessageStoreService grozaRetainMessageStoreService;

    public Subscribe(GrozaSubscribeStoreService grozaSubscribeStoreService,
                     GrozaMessageIdService grozaMessageIdService,
                     GrozaRetainMessageStoreService grozaRetainMessageStoreService) {
        this.grozaSubscribeStoreService = grozaSubscribeStoreService;
        this.grozaMessageIdService = grozaMessageIdService;
        this.grozaRetainMessageStoreService = grozaRetainMessageStoreService;
    }

    public void processSubscribe(Channel channel, MqttSubscribeMessage msg) {
        List<MqttTopicSubscription> topicSubscriptions = msg.payload().topicSubscriptions();
        if (this.validTopicFilter(topicSubscriptions)) {
            String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
            List<Integer> mqttQosList = new ArrayList<>();
            for (MqttTopicSubscription mqttTopicSubscription : topicSubscriptions) {
                String topicFilter = mqttTopicSubscription.topicName();
                MqttQoS mqttQoS = mqttTopicSubscription.qualityOfService();
                MqttSubAckMessage subAckMessage = (MqttSubAckMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.SUBACK, false, mqttQoS, false, 0),
                        MqttMessageIdVariableHeader.from(msg.variableHeader().messageId()),
                        new MqttSubAckPayload(mqttQosList));
                channel.writeAndFlush(subAckMessage);
                this.sendRetainMessage(channel, topicFilter, mqttQoS);

                SubscribeStore subscribeStore = new SubscribeStore(clientId, topicFilter, mqttQoS.value());
                grozaSubscribeStoreService.put(topicFilter, subscribeStore);
                mqttQosList.add(mqttQoS.value());
                log.info("SUBSCRIBE - clientId: {}, topFilter: {}, QoS: {}", clientId, topicFilter, mqttQoS.value());
            }
        } else {
            channel.close();
        }
    }

    private boolean validTopicFilter(List<MqttTopicSubscription> topicSubscriptions) {
        for (MqttTopicSubscription topicSubscription : topicSubscriptions) {
            String topicFilter = topicSubscription.topicName();
            // 以#或+符号开头的、以/符号结尾的订阅按非法订阅处理, 这里没有参考标准协议
            if (StrUtil.startWith(topicFilter, '+') || StrUtil.endWith(topicFilter, '/')) {
                return false;
            }
            if (StrUtil.contains(topicFilter, '#')) {
                // 如果出现多个#符号的订阅按非法订阅处理
                if (StrUtil.count(topicFilter, '#') > 1) {
                    return false;
                }
            }
            if (StrUtil.contains(topicFilter, '+')) {
                //如果+符号和/+字符串出现的次数不等的情况按非法订阅处理
                if (StrUtil.count(topicFilter, '+') != StrUtil.count(topicFilter, "/+")) {
                    return false;
                }

            }

        }
        return true;
    }

    private void sendRetainMessage(Channel channel, String topicFilter, MqttQoS mqttQoS) {
        List<RetainMessageStore> retainMessageStores = grozaRetainMessageStoreService.search(topicFilter);
        retainMessageStores.forEach(retainMessageStore -> {
            MqttQoS respQoS = retainMessageStore.getMqttQoS() > mqttQoS.value() ? mqttQoS : MqttQoS.valueOf(retainMessageStore.getMqttQoS());
            if (respQoS == MqttQoS.AT_MOST_ONCE) {
                MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.PUBLISH, false, respQoS, false, 0),
                        new MqttPublishVariableHeader(retainMessageStore.getTopic(), 0), Unpooled.buffer().writeBytes(retainMessageStore.getMessageBytes()));
                log.info("PUBLISH - clientId: {}, topic: {}, Qos: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), retainMessageStore.getTopic(), respQoS.value());
                channel.writeAndFlush(publishMessage);
            }
            if (respQoS == MqttQoS.AT_LEAST_ONCE) {
                int messageId = grozaMessageIdService.getNextMessageId();
                MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.PUBLISH, false, respQoS, false, 0),
                        new MqttPublishVariableHeader(retainMessageStore.getTopic(), messageId), Unpooled.buffer().writeBytes(retainMessageStore.getMessageBytes()));
                log.info("PUBLISH - clientId: {}, topic: {}, Qos: {}, messageId: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), retainMessageStore.getTopic(), respQoS.value(), messageId);
                channel.writeAndFlush(publishMessage);
            }
            if (respQoS == MqttQoS.EXACTLY_ONCE) {
                int messageId = grozaMessageIdService.getNextMessageId();
                MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.PUBLISH, false, respQoS, false, 0),
                        new MqttPublishVariableHeader(retainMessageStore.getTopic(), messageId), Unpooled.buffer().writeBytes(retainMessageStore.getMessageBytes()));
                log.info("PUBLISH - clientId: {}, topic: {}, Qos: {}, messageId: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), retainMessageStore.getTopic(), respQoS.value(), messageId);
                channel.writeAndFlush(publishMessage);
            }
        });
    }
}
