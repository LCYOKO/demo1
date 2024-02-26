package com.xiaomi.demo.java.spi;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/9
 */
@Slf4j
public class Test {
    @org.junit.Test
    public void test() {
        ServiceLoader<SearchService> serviceLoader = ServiceLoader.load(SearchService.class);
        Iterator<SearchService> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            SearchService service = iterator.next();
            log.info("getAllFiles:{}", service.getAllFiles("123"));
        }
    }
}
