package com.xiaomi.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/10
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }
}
