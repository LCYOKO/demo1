package com.xiaomi.demo;


import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuchiyun
 */
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class, DataSourceAutoConfiguration.class, RabbitAutoConfiguration.class})
@EnableAspectJAutoProxy
//@EnableApolloConfig
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
