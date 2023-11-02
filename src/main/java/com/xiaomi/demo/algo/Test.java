package com.xiaomi.demo.algo;

import com.google.common.collect.Lists;
import com.xiaomi.demo.data.People;
import com.xiaomi.demo.mq.kafka.KafkaConfig;
import lombok.Builder;
import lombok.Data;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import sun.nio.ch.DirectBuffer;

import javax.sound.midi.SysexMessage;
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
@Component
public class Test {


    public native void  sayHello();


//    @Bean
//    public TransactionTemplate transactionTemplate(DataSource dataSourceName){
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceName);
//        return new TransactionTemplate(transactionManager);
//    }
    private static final int _1MB = 1024*1024;

    private static int count;
    public static void main(String[] args) throws InterruptedException {
     count = 30;
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(()->doWhile("A",0)).start();
        new Thread(()->doWhile("B", 1)).start();
        new Thread(()->doWhile("C",2)).start();
        latch.await();
    }

    public static void doWhile(String symbol, int remain){
        while(count>0){
            if(count%3!=remain){
                Thread.yield();
                continue;
            }
            printSymbolAndCountdown(symbol);
        }

    }

    public static synchronized void printSymbolAndCountdown(String symbol){
        System.out.println(symbol);
        count--;
    }

    public void test(List<? extends Base> list){
        Base base = list.get(0);
    }
    public void test1(){
    }


    class Item <T extends Base>{

    }

    class Peoeple extends Base{

    }

    @Data
    class Base{
        private String data;
    }
}
