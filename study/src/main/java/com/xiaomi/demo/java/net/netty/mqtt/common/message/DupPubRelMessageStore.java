package com.xiaomi.demo.java.net.netty.mqtt.common.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchiyun
 * PUBREL重发消息存储
 */
@Data
@Builder
public class DupPubRelMessageStore implements Serializable {

    private static final long serialVersionUID = -4111642532532950980L;

    private String clientId;

    private int messageId;

}
