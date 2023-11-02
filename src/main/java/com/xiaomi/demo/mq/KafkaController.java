package com.xiaomi.demo.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.Kernel;

/**
 * @Author liuchiyun
 * @Date 2021/5/9 9:05 下午
 * @Version 1.0
 */
@RestController
public class KafkaController {

   @Autowired
   private ApplicationContext applicationContext;
  @Autowired(required = false)
    private KafkaTemplate<String,String> kafkaTemplate;
  @GetMapping("/send")
  public String send(String msg){
      kafkaTemplate.send("test",msg);
      return "ok";
  }
}
