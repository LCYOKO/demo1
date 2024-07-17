package com.xiaomi.demo.java.net.netty.mqtt.store.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaKafkaService;
import com.xiaomi.demo.java.net.netty.mqtt.internal.InternalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author liuchiyun
 */
@Service
public class KafkaServiceImpl implements GrozaKafkaService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    @Override
    public void send(InternalMessage internalMessage) {
        kafkaTemplate.send(internalMessage.getTopic(), gson.toJson(internalMessage));
    }
}
