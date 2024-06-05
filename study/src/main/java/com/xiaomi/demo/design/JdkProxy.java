package com.xiaomi.demo.design;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class JdkProxy implements InvocationHandler {
    private UserService target;

    public JdkProxy(UserService userService) {
        this.target = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("before");
        Object result = method.invoke(target, args);
        log.info("after");
        return result;
    }

    public static UserService getProxy(UserService userService) {
        return (UserService) Proxy.newProxyInstance(JdkProxy.class.getClassLoader(), userService.getClass().getInterfaces(), new JdkProxy(userService));
    }
}
