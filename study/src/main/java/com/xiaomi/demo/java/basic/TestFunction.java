package com.xiaomi.demo.java.basic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 * https://zhuanlan.zhihu.com/p/531651771
 */
@Slf4j
public class TestFunction {

    @Test
    public void testFunction1() {
        Function<Integer, Integer> identity = new Function<>() {
            @Override
            public Integer apply(Integer integer) {
                return integer;
            }

            @NotNull
            @Override
            public <V> Function<V, Integer> compose(@NotNull Function<? super V, ? extends Integer> before) {
                return Function.super.compose(before);
            }

            @NotNull
            @Override
            public <V> Function<Integer, V> andThen(@NotNull Function<? super Integer, ? extends V> after) {
                return Function.super.andThen(after);
            }
        };

        String apply1 = identity.andThen((Function<Integer, String>) integer -> "temp").apply(1);
        Integer apply2 = identity.compose((Function<Integer, Integer>) integer -> integer + 1).apply(1);

        log.info("apply1:{}, apply2:{}", apply1, apply2);


        Predicate<Integer> notNull = Objects::nonNull;
        boolean test1 = notNull.and((val) -> val % 2 == 0).test(1);
        boolean test2 = notNull.or(Objects::isNull).test(null);

        log.info("test1:{}, test2:{}", test1, test2);


        Consumer<String> printLog = log::info;
        printLog.andThen((val) -> log.info("after: {}", val)).accept("temp");

        Supplier<String> stringSupplier = () -> "temp";
    }

    @Test
    public void testFunction2() {
        Map<String, List<User>> collect = Stream.of(new User()).collect(Collectors.groupingBy(User::getGroup, Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)));
        BigDecimal total = Stream.of(BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);


    }

    @Test
    public void testFunction3() {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        Arrays.stream(arr).forEach(System.out::println);

        Stream.of(1, 2, 3, 4, 6).forEach(System.out::println);

        Stream.iterate(1, i -> i + 1).limit(10).forEach(System.out::println);

        Stream.generate(Math::random).limit(10).forEach(System.out::println);

        IntStream.rangeClosed(1, 10).forEach(System.out::println);
    }


    @Data
    static class User {
        private Long id;
        private String name;
        private String group;
    }
}
