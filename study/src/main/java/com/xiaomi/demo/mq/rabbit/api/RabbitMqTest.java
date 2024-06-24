package com.xiaomi.demo.mq.rabbit.api;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/24
 */
@Slf4j
public class RabbitMqTest {
    private Channel channel;
    private final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    private final String FANOUT_EXCHANGE_NAME = "fanout_exchange";
    private final String TOPIC_EXCHANGE_NAME = "topic_exchange";

    @Before
    public void init() throws IOException, TimeoutException {
        // 1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("myhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleNack(long deliveryTag, boolean multiple) {
                log.info("nAck deliverTag:{}", deliveryTag);
            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                log.info("ack deliverTag:{}", deliveryTag);
            }
        });

        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {

            log.error("---------handle  return----------");
            log.error("replyCode: " + replyCode);
            log.error("replyText: " + replyText);
            log.error("exchange: " + exchange);
            log.error("routingKey: " + routingKey);
            log.error("properties: " + properties);
            log.error("body: " + new String(body));
        });
    }

    @Test
    public void testSendDirect() throws IOException {
        String routingKey = "consumer.save";
        String msg = "Hello RabbitMQ Consumer Message";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, msg.getBytes());
        }
    }

    @Test
    public void testDirectConsumer() throws IOException {
        // 4.创建交换器
        // 因为不知道生产者和消费者程序哪个先启动，所以一般的做法是在生产者和消费者2边都创建交换器（有的话不会重复创建）
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 5.声明（创建）一个队列
        //  queue: 队列名字
        //  durable: 是否持久化
        //  exclusive: 是否独占
        //  autoDelete: 队列脱离exchange，自动删除
        //  arguments: 扩展参数
        final String queue1 = "test_queue1";
        final String bindingKey = "error";
        channel.queueDeclare(queue1, true, false, false, null);
        // 6.绑定交换机和队列
        channel.queueBind(queue1, DIRECT_EXCHANGE_NAME, bindingKey);
        channel.basicConsume(DIRECT_EXCHANGE_NAME, true, new MyConsumer(channel));
    }

    @Test
    public void testFanoutPublish() throws IOException {
        String[] logLevel = {"info", "warning", "error"};
        for (int i = 0; i < 3; i++) {
            String routingKey = logLevel[i % 3];
            String message = "hello rabbitmq " + i;
            channel.basicPublish(FANOUT_EXCHANGE_NAME, routingKey, null, message.getBytes());
            log.info("send message, routingKey: {}, message: {}", routingKey, message);
        }
    }

    @Test
    public void testFanoutConsumer() throws IOException {
        // 声明一个交换机
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueNameA = "allQueueA";
        String queueNameB = "allQueueB";
        String bindingKey = "info";

        channel.queueDeclare(queueNameA, false, false, false, null);
        channel.queueDeclare(queueNameB, false, false, false, null);
        channel.queueBind(queueNameA, FANOUT_EXCHANGE_NAME, bindingKey);
        channel.queueBind(queueNameB, FANOUT_EXCHANGE_NAME, bindingKey);

        channel.basicConsume(queueNameA, true, new MyConsumer(channel));
        channel.basicConsume(queueNameB, true, new MyConsumer(channel));
    }

    @Test
    public void testTopicSend() throws IOException {
        String[] logLevel = {"info", "warning", "error"};
        String[] module = {"driver", "login", "crm"};
        String[] score = {"A", "B", "C"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    String routingKey = String.join(",", Arrays.asList(logLevel[i % 3], module[j % 3], score[k % 3]));
                    String message = "hello rabbitmq routingKey is " + routingKey;
                    channel.basicPublish(TOPIC_EXCHANGE_NAME, routingKey, null, message.getBytes());
                    log.info("send message, routingKey: {}, message: {}", routingKey, message);
                }
            }
        }
    }

    @Test
    public void testTopicConsumer() throws IOException {
        // 声明一个交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueNameA = "allQueue";
        String bindingKeyA = "#";
        String queueNameB = "crmQueue";
        String bindingKeyB = "#.crm.#";
        channel.queueDeclare(queueNameA, false, false, false, null);
        channel.queueBind(queueNameA, TOPIC_EXCHANGE_NAME, bindingKeyA);
        channel.queueDeclare(queueNameB, false, false, false, null);
        channel.queueBind(queueNameB, TOPIC_EXCHANGE_NAME, bindingKeyB);
        channel.basicConsume(queueNameA, true, new MyConsumer(channel));
        channel.basicConsume(queueNameB, true, new MyConsumer(channel));
    }

    @Test
    public void testGetMsg() throws IOException, InterruptedException {
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        final String queue1 = "test_queue1";
        final String bindingKey = "error";
        channel.queueDeclare(queue1, true, false, false, null);
        channel.queueBind(queue1, DIRECT_EXCHANGE_NAME, bindingKey);
        while (true) {
            GetResponse getResponse = channel.basicGet(queue1, true);
            if (null != getResponse) {
                log.info("get message, routingKey: {}, message: {}", getResponse.getEnvelope().getRoutingKey(), new String(getResponse.getBody()));
            }
            Thread.sleep(1000);
        }
    }
}
