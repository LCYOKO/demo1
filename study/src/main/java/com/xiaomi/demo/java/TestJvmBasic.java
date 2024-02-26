package com.xiaomi.demo.java;

import lombok.Builder;
import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
public class TestJvmBasic {
    /**
     * JVM内存分区
     * 私有:
     * 线程栈: 保存栈帧(局部变量，返回地址等)
     * 程序计数器：保存当前线程下一条要执行的字节码
     * 本地线程栈：native线程执行需要用的栈
     * <p>
     * 共有:
     * 堆: 大部分new的对象和字符串常量池都在这里
     * 方法区:  存放类相关的信息和常量
     * 1.7 这个地方成为永久代，在堆中
     * 1.8 常量池放到堆中，类相关信息放堆外称元空间
     * <p>
     * 特殊：
     * 堆外：直接内存，DirectBuffer
     */
    public void test1() {

    }


    /**
     * 强引用：引用一直指向对象，直到手动置空
     * 软引用：SoftReference，引用对象直到，内存空间不足
     * 弱引用：WeekReference, 引用对象直到，发生gc
     * 虚引用：PhantomReference, 虚引用获取不到对象，一般用做GC回调, 例如Cleaner,用来回收DirectBuffer
     */
    public void test2() {
        //强引用
        Car car = Car.builder().build();
        //软引用
        SoftReference<Car> softReference = new SoftReference<>(car);
        //弱引用
        WeakReference<Car> weakReference = new WeakReference<>(car);
        //虚引用
        PhantomReference<Car> phantomReference = new PhantomReference<>(car, new ReferenceQueue<>());
    }

    /**
     * MarkWord的构成如下：
     * ------------------------------------------------------------------------------|-----------|
     * Mark Word(64 bits)                                        |  锁状态   |
     * ------------------------------------------------------------------------------|-----------|
     * unused:25 | identity_hashcode:31 | unused:1 | age:4 | biased_lock:0 | lock:01 |  正  常   |
     * ------------------------------------------------------------------------------|-----------|
     * thread:54 |    epoch:2    |        unused:1 | age:4 | biased_lock:1 | lock:01 |  偏向锁   |
     * ------------------------------------------------------------------------------|-----------|
     * ptr_to_lock_record:62                           | lock:00 |  轻量级锁 |
     * ------------------------------------------------------------------------------|-----------|
     * ptr_to_heavyweight_monitor:62                       | lock:11 |  重量级锁 |
     * ------------------------------------------------------------------------------|-----------|
     * | lock:11 |  GC标记   |
     * ------------------------------------------------------------------------------|-----------|
     * <p>
     * 1、lock。2位，锁状态的标记位，
     * 2、biased_lock。1位。对象是否存在偏向锁标记。lock与biased_lock共同表示锁对象处于什么锁状态。
     * 3、age。4位，表示JAVA对象的年龄，在GC中，当survivor区中对象复制一次，年龄加1，如果到15之后会移动到老年代，并发GC的年龄阈值为6.
     * 4、identity_hashcode。31位，调用方法 System.identityHashCode()计算，并会将结果写到该对象头中。当对象加锁后（偏向、轻量级、重量级），MarkWord的字节没有足够的空间保存hashCode，因此该值会移动到线程 Monitor中。
     * 5、thread。54位，持有偏向锁的线程ID（此处的线程id是操作系统层面的线程唯一Id,与java中的线程id是不一致的，了解即可）。
     * 6、epoch。2位，偏向锁的时间戳。
     * 7、ptr_to_lock_record。62位，轻量级锁状态下，指向栈中锁记录的指针。
     * 8、ptr_to_heavyweight_monitor。62位，重量级锁状态下，指向对象监视器 Monitor的指针。
     * <p>
     * -XX:-UseCompressedClassPointers  开启压缩指针
     */
    public void test3() {
        //  计算对象的大小（单位为字节）：ClassLayout.parseInstance(obj).instanceSize()
        //  查看对象内部信息： ClassLayout.parseInstance(obj).toPrintable()
        //  查看对象外部信息：包括引用的对象：GraphLayout.parseInstance(obj).toPrintable()
        //  查看对象占用空间总大小：GraphLayout.parseInstance(obj).totalSize()
        Car car = Car.builder()
                .id(1)
                .type("SUV")
                .level('A')
                .price(22.22)
                .build();
        System.out.println(ClassLayout.parseInstance(car).toPrintable());
        int[] array = new int[3];
        array[0] = 11;
        array[1] = 22;
        array[2] = 33;
        System.out.println(ClassLayout.parseInstance(array).toPrintable());
    }

    @Data
    @Builder
    static class Car {
        private int id;
        private String type;
        private double price;
        private char level;
    }
}
