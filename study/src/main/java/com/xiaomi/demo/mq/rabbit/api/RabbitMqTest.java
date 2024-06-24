package com.xiaomi.demo.mq.rabbit.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/24
 */
public class RabbitMqTest {
    private Channel channel;
    public final String DIRECT_EXCHANGE_NAME = "quickStart_direct_exchange";
    private final String QUEUE_NAME1 = "test_queue1";
    private final String bindingKey = "error";

    @Before
    public void init() throws IOException, TimeoutException {
        // 1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("myhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        // 2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
    }

    @Test
    public void testSimpleSend() throws IOException {
        String routingKey = "consumer.save";
        String msg = "Hello RabbitMQ Consumer Message";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, true, null, msg.getBytes());
        }
    }

    @Test
    public void testBasicConsumer() throws IOException {
        // 4.创建交换器
        // 因为不知道生产者和消费者程序哪个先启动，所以一般的做法是在生产者和消费者2边都创建交换器（有的话不会重复创建）
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 5.声明（创建）一个队列
        //  queue: 队列名字
        //  durable: 是否持久化
        //  exclusive: 是否独占
        //  autoDelete: 队列脱离exchange，自动删除
        //  arguments: 扩展参数
        channel.queueDeclare(QUEUE_NAME1, true, false, false, null);
        // 6.绑定交换机和队列
        channel.queueBind(QUEUE_NAME1, DIRECT_EXCHANGE_NAME, bindingKey);
        channel.basicConsume(DIRECT_EXCHANGE_NAME, true, new MyConsumer(channel));
    }

}
