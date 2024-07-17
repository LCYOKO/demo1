/**
 * Copyright (c) 2018, Mr.Wang (recallcode@aliyun.com) All rights reserved.
 */

package com.xiaomi.demo.java.net.netty.mqtt.common.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchiyun
 */
@Data
@Builder
public class RetainMessageStore implements Serializable {

    private static final long serialVersionUID = -7548204047370972779L;

    private String topic;

    private byte[] messageBytes;

    private int mqttQoS;
}
