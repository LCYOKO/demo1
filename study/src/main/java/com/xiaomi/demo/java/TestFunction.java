package com.xiaomi.demo.java;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
@Slf4j
public class TestFunction {

    @Test
    public void testFunction1() {
        Function<Integer, Integer> identity = integer -> integer;

        Predicate<Integer> notNull = (num) -> num != null;

        Consumer<String> printLog = (logStr) -> log.info(logStr);

        Supplier<String> stringSupplier = () -> "temp";
    }

    @Test
    public void testFunction2() {
        Map<String, List<User>> collect = Collections.singleton(new User()).stream().collect(Collectors.groupingBy(User::getGroup, Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)));
        BigDecimal total = Collections.singleton(BigDecimal.ZERO).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Data
    static class User {
        private Long id;
        private String name;
        private String group;
    }
}
