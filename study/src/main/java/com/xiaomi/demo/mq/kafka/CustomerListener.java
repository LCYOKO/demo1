package com.xiaomi.demo.mq.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ConsumerAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Author liuchiyun
 * @Date 2021/6/14 2:50 下午
 * @Version 1.0
 */
//@Component
public class CustomerListener implements ConsumerAwareMessageListener<String,String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> data, Consumer<?, ?> consumer) {
        System.err.println(data+"123");
        consumer.commitSync();
    }
}
