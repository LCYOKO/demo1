package com.xiaomi.demo.mq.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.Message;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author liuchiyun
 * @Date 2021/5/9 7:07 下午
 * @Version 1.0
 */
@Component
public class MyConsumer {

    @KafkaListener(topics = {"test"},
    groupId = "myGroup")
    public void receive(@Payload Message message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        Consumer consumer) {
        System.out.println(String.format("From partition %d : %s", partition, message));
        // 同步提交
        consumer.commitSync();

        // ack这种方式提交也可以
        // ack.acknowledge();
    }

}
