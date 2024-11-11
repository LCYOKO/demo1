package com.xiaomi.demo.mq.kafka;

import org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignor;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/11/11
 */
public class CustomerAssignor extends AbstractPartitionAssignor {
    @Override
    public Map<String, List<TopicPartition>> assign(Map<String, Integer> partitionsPerTopic, Map<String, Subscription> subscriptions) {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}
