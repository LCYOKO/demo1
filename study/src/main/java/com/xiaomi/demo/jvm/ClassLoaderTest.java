package com.xiaomi.demo.jvm;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/15
 */
public class ClassLoaderTest {

    /**
     * 1 加载是指查找字节流，并且据此创建类的过程
     * 2 验证,在于确保被加载类能够满足 Java 虚拟机的约束条件
     * 3 准备,则是为被加载类的静态字段分配内存
     * 4 解析阶段的目的，正是将这些符号引用解析成为实际引用
     * 5 初始化，则是为标记为常量值的字段赋值，以及执行 < clinit > 方法的过程。类的初始化仅会被执行一次，这个特性被用来实现单例的延迟初始化
     */
    public void test1(){

    }
}
