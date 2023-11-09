package com.xiaomi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

/**
 * @author liuchiyun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
//@EnableApolloConfig
@EnableTransactionManagement

//@EnableNacosDiscovery
@EnableAspectJAutoProxy
//@NacosPropertySource(dataId = "test", autoRefreshed = true)
public class DemoApplication {


    @PostConstruct
    public void init() {
        //namingService.registerInstance("hahaha","127.0.0.1",8112);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
