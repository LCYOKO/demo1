package com.xiaomi.demo.mq.rabbit.api;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author liuchiyun
 */
@Slf4j
public class MyConsumer extends DefaultConsumer {
    private String name;

    public MyConsumer(Channel channel) {
        super(channel);
    }

    public MyConsumer(Channel channel, String name) {
        super(channel);
        this.name = name;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        log.info("name:{}, tag:{}, envelope:{}, properties:{}, msg:{}", name, consumerTag, envelope, properties, new String(body));

        // checked消息会被删除
        // unchecked的消息会等到channel重启后，才会变成ready等待消费
//        channel.basicAck(envelope.getDeliveryTag(), false);
        // Nack可以批量拒绝，拒绝后如果要重回队列会回到队列首部
//        channel.basicNack(envelope.getDeliveryTag(), false, false);
        // 和Nack一样，但是一次只能拒绝一条
        //  channel.basicReject(envelope.getDeliveryTag(), false);
    }
}
