package com.xiaomi.demo.spring.mvc;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @Author l
 * @Date 2021/2/13 22:57
 * @Version 1.0
 *
 *
 *
 *
 */
public class Test {

    /**
     * -------------------------SpringMVC处理请求流程----------------------------
     * 1）dispatchServlet,从HandlerMapping获取mappedHandler
     * 2）获取handlerAdapter
     * 3）调用拦截器的preHandle
     * 4）直接handlerAdapter.handle 生成ModelAndView
     * 5) 调用拦截器的postHandle
     * 6）进行调用viewResolve进行渲染
     * 7）调用拦截器的afterCompletion
     *
     *
     *
     * ------------------------RequestMapping解析-----------------------------
     * WebMvcConfigurationSupport 注解类
     * RequestMappingHandlerMapping 入口类
     * RequestCondition 匹配条件接口，通过其实现类来判断是否匹配处理方法
     * RequestMappingInfo 匹配条件信息类，实现RequestCondition，并包含多个RequestCondition实现类
     */
    public static void main(String[] args) throws LifecycleException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(80);
        //
        Context context = tomcat.addContext("/", System.getProperty("java.io.tmpdir"));
        //只会去初始化一个 context的资源目录 并不会加载 web的生命周期
        // webapps
        // .war   文件夹
        //        tomcat.addWebapp("/","C:\\Program Files\\pro\\public-luban-project\\spring-mvc\\src\\main\\webapp");
        context.addLifecycleListener((LifecycleListener) Class.forName(tomcat.getHost().getConfigClass()).newInstance());
        tomcat.start();

        tomcat.getServer().await();
       DispatcherServlet dispatcherServlet = new DispatcherServlet();
    }
}
