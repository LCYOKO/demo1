package com.xiaomi.demo.algo;

import com.xiaomi.demo.mq.kafka.KafkaConfig;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import sun.nio.ch.DirectBuffer;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 */
public class Test {


    public native void  sayHello();


    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSourceName){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceName);
        return new TransactionTemplate(transactionManager);
    }

    public static void main(String[] args) {
        KafkaConfig
    }
}
