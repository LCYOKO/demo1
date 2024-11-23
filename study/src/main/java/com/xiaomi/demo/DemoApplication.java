package com.xiaomi.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author liuchiyun
 */
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class, RabbitAutoConfiguration.class})
@EnableAspectJAutoProxy
@MapperScan("com.xiaomi.demo.orm")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
