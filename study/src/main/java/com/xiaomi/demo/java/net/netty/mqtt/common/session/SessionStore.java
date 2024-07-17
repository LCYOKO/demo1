package com.xiaomi.demo.java.net.netty.mqtt.common.session;


import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchiyun
 */
@Data
public class SessionStore implements Serializable {
    private static final long serialVersionUID = 5209539791996944490L;

    private String clientId;

    private Channel channel;

    private boolean cleanSession;

    private MqttPublishMessage willMessage;

    public SessionStore(String clientId, Channel channel, boolean cleanSession, MqttPublishMessage willMessage) {
        this.clientId = clientId;
        this.channel = channel;
        this.cleanSession = cleanSession;
        this.willMessage = willMessage;
    }
}
