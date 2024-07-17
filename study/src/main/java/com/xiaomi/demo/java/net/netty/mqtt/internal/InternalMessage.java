package com.xiaomi.demo.java.net.netty.mqtt.internal;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchiyun
 */
@Data
@Builder
public class InternalMessage implements Serializable {
    public static final long serialVersionUID = -1L;

    private String clientId;

    private String topic;

    private int mqttQoS;

    private byte[] messageBytes;

    private boolean retain;

    private boolean dup;

}
