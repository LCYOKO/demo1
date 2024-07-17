package com.xiaomi.demo.java.net.netty.mqtt.protocol;


import com.xiaomi.demo.java.net.netty.mqtt.common.auth.GrozaAuthService;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.*;
import com.xiaomi.demo.java.net.netty.mqtt.common.session.GrozaSessionStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.GrozaSubscribeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuchiyun
 */
@Component
public class ProtocolProcess {
    @Autowired
    private GrozaSessionStoreService grozaSessionStoreService;
    @Autowired
    private GrozaSubscribeStoreService grozaSubscribeStoreService;
    @Autowired
    private GrozaAuthService grozaAuthService;
    @Autowired
    private GrozaMessageIdService grozaMessageIdService;
    @Autowired
    private GrozaRetainMessageStoreService grozaRetainMessageStoreService;
    @Autowired
    private GrozaDupPublishMessageStoreService grozaDupPublishMessageStoreService;
    @Autowired
    private GrozaDupPubRelMessageStoreService grozaDupPubRelMessageStoreService;
    @Autowired
    private GrozaKafkaService kafkaService;

    private Connect connect;

    private Subscribe subscribe;

    private UnSubscribe unSubscribe;

    private Publish publish;

    private DisConnect disConnect;

    private PubRel pubRel;

    private PubAck pubAck;

    private PubRec pubRec;

    private PubComp pubComp;

    public Connect connect() {
        if (connect == null) {
            connect = new Connect(grozaAuthService, grozaSessionStoreService, grozaDupPublishMessageStoreService, grozaDupPubRelMessageStoreService, grozaSubscribeStoreService);
        }
        return connect;
    }

    public Subscribe subscribe() {
        if (subscribe == null) {
            subscribe = new Subscribe(grozaSubscribeStoreService, grozaMessageIdService, grozaRetainMessageStoreService);
        }
        return subscribe;
    }

    public UnSubscribe unSubscribe() {
        if (unSubscribe == null) {
            unSubscribe = new UnSubscribe(grozaSubscribeStoreService);
        }
        return unSubscribe;
    }

    public Publish publish() {
        if (publish == null) {
            publish = new Publish(grozaSessionStoreService, grozaSubscribeStoreService, grozaMessageIdService, grozaRetainMessageStoreService, grozaDupPublishMessageStoreService, kafkaService);
        }
        return publish;
    }

    public DisConnect disConnect() {
        if (disConnect == null) {
            disConnect = new DisConnect(grozaSessionStoreService, grozaSubscribeStoreService, grozaDupPublishMessageStoreService, grozaDupPubRelMessageStoreService);
        }
        return disConnect;
    }

    public PubRel pubRel() {
        if (pubRel == null) {
            pubRel = new PubRel();
        }
        return pubRel;
    }

    public PubAck pubAck() {
        if (pubAck == null) {
            pubAck = new PubAck(grozaDupPublishMessageStoreService);
        }
        return pubAck;
    }

    public PubRec pubRec() {
        if (pubRec == null) {
            pubRec = new PubRec(grozaDupPublishMessageStoreService, grozaDupPubRelMessageStoreService);
        }
        return pubRec;
    }

    public PubComp pubComp() {
        if (pubComp == null) {
            pubComp = new PubComp(grozaDupPubRelMessageStoreService);
        }
        return pubComp;
    }

    public GrozaSessionStoreService getGrozaSessionStoreService() {
        return grozaSessionStoreService;
    }
}
