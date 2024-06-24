package com.xiaomi.demo.mq.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/24
 */
@Slf4j
public class RocketMqTest {
    private DefaultMQProducer publishTemplate;

    @Before
    public void init() {
        publishTemplate = new DefaultMQProducer("testProducer");
        publishTemplate.setNamesrvAddr("localhost:9876");
    }

    @Test
    public void testSimpleSend() throws MQClientException {
        Message msg = new Message("test", ("Hello RocketMQ ").getBytes());
        try {
            SendResult sendResult = publishTemplate.send(msg);
            System.out.printf("%s%n", sendResult);
        } catch (Exception e) {
            log.info("send message failed. message:{}", msg, e);
        }
    }

    @Test
    public void testBatchSend() throws MQClientException {
        Message msg = new Message("test", ("Hello RocketMQ ").getBytes());
        try {
            SendResult sendResult = publishTemplate.send(Collections.singletonList(msg));
            System.out.printf("%s%n", sendResult);
        } catch (Exception e) {
            log.info("send message failed. message:{}", msg, e);
        }
    }

//    @Test
//    public void testSendOrderly() throws MQClientException {
//        Message msg = new Message("test", ("Hello RocketMQ ").getBytes());
//        try {
//            SendResult sendResult = publishTemplate.send(msg, Long.parseLong("order"));
//            System.out.printf("%s%n", sendResult);
//        } catch (Exception e) {
//            log.info("send message failed. message:{}", msg, e);
//        }
//    }
}
