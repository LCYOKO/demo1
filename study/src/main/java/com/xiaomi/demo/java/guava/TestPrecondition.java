package com.xiaomi.demo.java.guava;

import com.google.common.base.Preconditions;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
public class TestPrecondition {

    @Test
    public void testPreconditions() {
        Preconditions.checkNotNull(new Object());
    }
}
