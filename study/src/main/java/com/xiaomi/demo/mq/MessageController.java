package com.xiaomi.demo.mq;

import com.xiaomi.demo.mq.rabbit.boot.RabbitPushTemplate;
import com.xiaomi.demo.mq.rabbit.boot.entity.Order;
import com.xiaomi.demo.orm.mybatis.UserMapper;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @Author liuchiyun
 * @Date 2021/5/9 9:05 下午
 */
@RestController
@RequestMapping("/mq")
public class MessageController {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RabbitPushTemplate rabbitPushTemplate;

    @GetMapping("/send")
    public String send(String msg) {
        kafkaTemplate.send("test", msg);
        return "ok";
    }

    @GetMapping("/rabbit/send")
    public void rabbitSend(String msg) {
        rabbitPushTemplate.send(msg, Collections.emptyMap());
    }

    @GetMapping("/rabbit/send/order")
    public void rabbitSendOrder(Order order) {
        rabbitPushTemplate.sendOrder(order);
    }
}
