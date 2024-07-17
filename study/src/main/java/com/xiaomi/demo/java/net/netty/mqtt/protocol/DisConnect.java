package com.xiaomi.demo.java.net.netty.mqtt.protocol;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPubRelMessageStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPublishMessageStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.session.GrozaSessionStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.session.SessionStore;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.GrozaSubscribeStoreService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuchiyun
 */
@Slf4j
public class DisConnect {

    private final GrozaSessionStoreService grozaSessionStoreService;

    private final GrozaSubscribeStoreService grozaSubscribeStoreService;

    private final GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService;

    private final GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService;

    public DisConnect(GrozaSessionStoreService grozaSessionStoreService,
                      GrozaSubscribeStoreService grozaSubscribeStoreService,
                      GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService,
                      GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService) {
        this.grozaSessionStoreService = grozaSessionStoreService;
        this.grozaSubscribeStoreService = grozaSubscribeStoreService;
        this.grozaDupPublishMessageStoreService = grozaDupPublishMessageStoreService;
        this.grozaDupPubRelMessageStoreService = grozaDupPubRelMessageStoreService;
    }

    public void processDisConnect(Channel channel, MqttMessage msg) {
        String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
        SessionStore sessionStore = grozaSessionStoreService.get(clientId);
        if (sessionStore != null && sessionStore.isCleanSession()) {
            grozaSubscribeStoreService.removeForClient(clientId);
            grozaDupPublishMessageStoreService.removeByClient(clientId);
            grozaDupPubRelMessageStoreService.removeByClient(clientId);
        }
        log.info("DISCONNECT - clientId: {}, cleanSession: {}", clientId, sessionStore.isCleanSession());
        grozaSessionStoreService.remove(clientId);
        channel.close();
    }

}
