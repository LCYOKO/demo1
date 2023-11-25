package com.xiaomi.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuchiyun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableApolloConfig
//@NacosPropertySource(dataId = "test", autoRefreshed = true)
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        environment.getActiveProfiles();
    }

}
