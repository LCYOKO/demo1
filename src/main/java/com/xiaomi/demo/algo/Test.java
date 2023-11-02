package com.xiaomi.demo.algo;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 */
public class Test {

    public native void sayHello();


    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSourceName) {
        DelimiterBasedFrameDecoder
                ByteBuf byteBuf;
        byteBuf.readableBytes()=0
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceName);
        return new TransactionTemplate(transactionManager);
    }
}
