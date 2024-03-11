package com.xiaomi.demo.spring.boot;

import com.xiaomi.demo.DemoApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
public class Test {

    /**
     * -----------------------SpringBoot启动流程-------------------------
     * 1）run方法后面会生成一个springBootApplication
     * 2）创建的时候就确认当前工作环境
     * 3）继续调用application的run方法
     * 3.1 获取 banner
     * 3.2 根据当前环境创建Spring容器
     * 3.3 fresh容器 就是调用容器的refresh()方法
     * 3.3.1 如果是web容器，那么onRefresh方法会创建Tomcat对象
     * 3.4 afterRefresh 执行回调方法
     * ----------------------SpringBoot自动装配-------------------------
     * 1）SpringBootApplication内部是用一个Import注解导入一个 selector
     * 2）该selector会遍历所有的META-INF/spring.factories文件，把对象的全类名添加到集合
     * 3）直接返回该集合，并注册到BD递归解析。
     */
    public static void main(String[] args) {
//        @SpringBootApplication
        SpringApplication.run(DemoApplication.class, args);
    }
}
