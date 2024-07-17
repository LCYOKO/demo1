package com.xiaomi.demo.java.net.netty.mqtt.common.subscribe;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchiyun
 * 订阅存储实体类
 */
@Data
public class SubscribeStore implements Serializable {
    private static final long serialVersionUID = 1276156087085594264L;

    private String clientId;

    private String topicFilter;

    private int mqttQoS;

    public SubscribeStore(String clientId, String topicFilter, int mqttQoS) {
        this.clientId = clientId;
        this.topicFilter = topicFilter;
        this.mqttQoS = mqttQoS;
    }
}
