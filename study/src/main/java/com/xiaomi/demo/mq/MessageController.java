package com.xiaomi.demo.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liuchiyun
 * @Date 2021/5/9 9:05 下午
 */
@RestController
public class MessageController {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public String send(String msg) {
        kafkaTemplate.send("test", msg);
        return "ok";
    }
}
