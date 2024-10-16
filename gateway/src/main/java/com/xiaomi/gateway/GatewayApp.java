package com.xiaomi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/10
 * https://mp.weixin.qq.com/s/IXInfuWkKe5D1fmtpQQ1SA
 * https://developer.aliyun.com/article/1281193
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RabbitAutoConfiguration.class, RedisAutoConfiguration.class})
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);

    }
}
