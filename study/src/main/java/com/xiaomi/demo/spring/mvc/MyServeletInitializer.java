package com.xiaomi.demo.spring.mvc;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @Author l
 * @Date 2021/2/13 23:22
 * @Version 1.0
 */
public class MyServeletInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("123");
    }
}
