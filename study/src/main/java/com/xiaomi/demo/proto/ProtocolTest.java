package com.xiaomi.demo.proto;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/9
 * https://protobuf.dev/
 */
@Slf4j
public class ProtocolTest {
    @Test
    public void test1() {
        BookRequest request = BookRequest.newBuilder()
                .setId(1)
                .setName(com.google.protobuf.StringValue.newBuilder().setValue("bookName").build())
                .build();
        log.info("bookNameIsSet:{},bookName:{}", request.hasName(), request.getName());
    }
}
