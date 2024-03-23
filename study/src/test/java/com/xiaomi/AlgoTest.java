package com.xiaomi;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @Author: liuchiyun
 * @Date: 2024/3/7
 */
public class AlgoTest {

    @Test
    public void test1() {
        Locale locale = Locale.forLanguageTag("zh_CN");
        Assert.assertNotNull(locale);
    }
}
