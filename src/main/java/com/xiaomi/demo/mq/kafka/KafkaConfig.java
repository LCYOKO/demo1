package com.xiaomi.demo.mq.kafka;

import org.apache.kafka.common.Cluster;
import org.apache.tomcat.util.file.ConfigFileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @Author liuchiyun
 * @Date 2021/5/9 9:06 下午
 * @Version 1.0
 */
@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(ProducerFactory<String,String> factory){
        return  new KafkaTemplate(factory);

    }
}
