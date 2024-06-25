package com.xiaomi.demo.mq.rabbit.boot;

import com.xiaomi.demo.mq.rabbit.boot.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liuchiyun
 * spring.rabbitmq.addresses=192.168.11.76:5672
 * spring.rabbitmq.username=guest
 * spring.rabbitmq.password=guest
 * spring.rabbitmq.virtual-host=/
 * spring.rabbitmq.connection-timeout=15000
 * <p>
 * spring.rabbitmq.publisher-confirms=true
 * spring.rabbitmq.publisher-returns=true
 * spring.rabbitmq.template.mandatory=true
 */

@Component
@Slf4j
public class RabbitPushTemplate {

    /**
     * 自动注入RabbitTemplate模板类
     */
    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息方法调用: 构建Message消息
     */
    public void send(Object message, Map<String, Object> properties) {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message<Object> msg = MessageBuilder.createMessage(message, mhs);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend("topic001", "spring.abc", msg, correlationData);
    }

    /**
     * 发送消息方法调用: 构建自定义对象消息
     */
    public void sendOrder(Order order) {
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("0987654321");
        rabbitTemplate.convertAndSend("topic001", "spring.def", order, correlationData);
    }
}
