package com.xiaomi.demo.design;


import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("before");
        Object result = methodProxy.invokeSuper(o, objects);
        log.info("after");
        return result;
    }

    public static UserService getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new CglibProxy());
        // 创建代理对象
        return (UserService) enhancer.create();
    }
}
