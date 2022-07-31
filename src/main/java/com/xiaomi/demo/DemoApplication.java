package com.xiaomi.demo;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import com.xiaomi.demo.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sun.nio.ch.DirectBuffer;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author liuchiyun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
//@EnableApolloConfig
@EnableTransactionManagement
@EnableEurekaClient
//@EnableNacosDiscovery
@EnableAspectJAutoProxy
//@NacosPropertySource(dataId = "test", autoRefreshed = true)
public class DemoApplication {

    @NacosInjected
    private NamingService namingService;

    @PostConstruct
    public void init() throws NacosException {
        //namingService.registerInstance("hahaha","127.0.0.1",8112);
    }
    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
        System.out.println("123");
        new MyService().sayHello();
    }
}
