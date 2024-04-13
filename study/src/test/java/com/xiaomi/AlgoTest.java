package com.xiaomi;

import org.apache.rocketmq.common.CountDownLatch2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: liuchiyun
 * @Date: 2024/3/7
 */
public class AlgoTest {

    @Test
    @DisplayName("测试1")
    public void test1() {
        Locale locale = Locale.forLanguageTag("zh_CN");
        Assert.assertNotNull(locale);
    }
}
