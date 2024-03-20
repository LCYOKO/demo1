package com.xiaomi.demo.spring.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 7:36 下午
 * @Version 1.0
 */
@MapperScan
public class Test {
    /**
     * ---------------------------------容器初始化---------------------------------
     * 1) AnnotationConfigApplicationContext初始化的时候会创建2个类  BeanDefinitionReader BeanDefinitionScanner
     * 1.1 BeanDefinitionReader的作用是注册注解配置类，创建的时候会注册6个BD
     * 1.1.1  ConfigurationClassPostProcessor, AutowiredBeanPostProcessor, CommonBeanPostProcessor,
     * 1.2 BeanDefinitionScanner的作用是调用容器主动扫描。
     * 2）postProcessBeanFactory()
     * 对容器进行初始化，一般用作整合SpringMVC
     * 3）invokeBeanFactoryPostProcessor()
     * 调用beanFactoryPostProcessor找到所有的前置处理器并执行,在初始化AnnotationContext的时候会注册几个Bd
     * 其中包含一个ConfigurationClassPostProcessor 完成
     * 3.1调用所有实现priority的BeanDefinitionRegistryPostProcess
     * 3.1.1 postProcessBeanDefinitionRegistry方法中完成bean的注册
     * 3.1.2 处理Import注解中导入的class，实现ImportSelector接口的对象   ImportBeanDefinitionRegistrar接口的对象
     * 3.2调用所有实现order的BeanDefinitionRegistryPostProcess
     * 3.3调用剩下的BeanDefinitionRegistryPostProcess  完成 beanDefinition的注册
     * 3.4调用普通的BeanFactoryPostProcessor() 完成前置处理
     * 4）调用registerBeanPostProcessor  完成BeanPostProcessor
     * 5）接下来就是初始化消息源，时间监听器啥的
     * MapperScan注解是是注册 MapperScannerConfigurer   改类实现了BeanDefinitionRegistryPostProcessor完成自己
     * 的注册逻辑
     * <p>
     * ---------------------------------Bean的生命周期----------------------------------
     * 方法入口org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
     * 1）调用 InstantiationAwareBeanPostProcessor的before方法  如果能提前获得对象则，继续调用after方法返回
     * 2）构造方法推断，选择最合适的构造方法并实例化
     * 3）调用 InstantiationAwareBeanPostProcessor的after方法，如果结果返回false 则不继续属性自动注入
     * 4）判断对象是否实现Aware接口，如果实现调用相应的set方法
     * 5）循环遍历调用BeanPostProcessor   beforeInitial方法  完成前置处理 解析PostConstruct等注解
     * 6）调用生命周期回调方法，按照 afterPropertiesSet，init这样的顺序
     * 5）循环遍历调用BeanPostProcessor   afterInitial方法   完成动态代理
     * 6）把bean放入 singletonObjects  清空earlySingletonObjects和singletonFactories;
     */
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
    }
}
