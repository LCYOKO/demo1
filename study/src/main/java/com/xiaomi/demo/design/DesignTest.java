package com.xiaomi.demo.design;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
public class DesignTest {

    @Test
    public void test1() {
        UserService userService = new UserServiceImpl();
        UserService proxy = JdkProxy.getProxy(userService);
        User user = proxy.selectOne();
        Assert.assertNotNull(user);
    }

    @Test
    public void test2() {
        UserService proxy = CglibProxy.getProxy();
        User user = proxy.selectOne();
        Assert.assertNotNull(user);
    }
}
