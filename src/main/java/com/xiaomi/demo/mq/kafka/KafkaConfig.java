package com.xiaomi.demo.mq.kafka;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.tomcat.util.file.ConfigFileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liuchiyun
 * @Date 2021/5/9 9:06 下午
 * @Version 1.0
 */
@Configuration
public class KafkaConfig {


    @Bean
    public ProducerFactory<String,String>  producerFactory(){
        Map<String,Object> props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG,"-1");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, Lists.newArrayList(CustomerInterceptor.class));
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,CustomerPartitioner.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(ProducerFactory<String,String> factory){
        return  new KafkaTemplate<>(factory);
    }
}
