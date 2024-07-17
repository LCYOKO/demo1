package com.xiaomi.demo.java.net.netty.mqtt.common.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchiyun
 */
@Data
@Builder
public class DupPublishMessageStore implements Serializable {
    private static final long serialVersionUID = -8112511377194421600L;
    private String clientId;
    private String topic;
    private int mqttQoS;
    private int messageId;
    private byte[] messageBytes;
}
