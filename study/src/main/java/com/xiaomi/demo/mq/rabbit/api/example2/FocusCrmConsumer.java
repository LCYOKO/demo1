package com.xiaomi.demo.mq.rabbit.api.example2;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FocusCrmConsumer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("myhost");
        connectionFactory.setPort(5672);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 声明一个交换机
        channel.exchangeDeclare(TopicExchangeProducer.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = "crmQueue";
        String bindingKey = "#.crm.#";

        channel.queueDeclare(queueName, false, false, false ,null);
        channel.queueBind(queueName, DirectExchangeProducer.EXCHANGE_NAME, bindingKey);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                log.info("get message, routingKey: {}, message: {}", envelope.getRoutingKey(), message);
            }
        };

        channel.basicConsume(queueName , true, consumer);
    }
}
