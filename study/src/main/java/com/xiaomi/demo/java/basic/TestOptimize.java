package com.xiaomi.demo.java.basic;

import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
public class TestOptimize {
    /**
     * 即时编译
     * 从 Java 8 开始，Java 虚拟机默认采用分层编译的方式。
     * 它将执行分为五个层次，分为
     * 0 层解释执行，
     * 1 层执行没有 profiling 的 C1 代码，
     * 2 层执行部分 profiling 的 C1 代码，
     * 3 层执行全部 profiling 的 C1 代码，
     * 4 层执行 C2 代码。
     * <p>
     * 即时编译是由方法调用计数器和循环回边计数器触发的。在使用分层编译的情况下，
     * 触发编译的阈值是根据当前待编译的方法数目动态调整的
     */
    @Test
    public void test1() {

    }

    /**
     * 方法内联
     * 方法内联能够触发更多的优化。通常而言，内联越多，生成代码的执行效率越高。
     * 然而，对于即时编译器来说，内联越多，编译时间也就越长，而程序达到峰值性能的时刻也将被推迟。
     * 此外，内联越多也将导致生成的机器码越长
     */
    @Test
    public void test2() {

    }

    /**
     * 逃逸分析
     * 即时编译器可以根据逃逸分析的结果进行诸如锁消除、栈上分配以及标量替换的优化
     */
    @Test
    public void test3() {

    }

    /**
     * 死代码和冗余优化
     */
    @Test
    public void test4() {

    }

    /**
     * 循环展开
     */
    public void test5() {
        //循环展开
        //循环展开的缺点显而易见：它可能会增加代码的冗余度，导致所生成机器码的长度大幅上涨。
        //不过，随着循环体的增大，优化机会也会不断增加。一旦循环展开能够触发进一步的优化，总体的代码复杂度也将降低
        {
            //未展开
            int[] a = new int[100];
            int sum = 0;
            for (int i = 0; i < 64; i++) {
                sum += (i % 2 == 0) ? a[i] : -a[i];
            }

            //展开
            for (int i = 0; i < 64; i += 2) {
                // 注意这里的步数是 2
                sum += a[i];
                sum += -a[i + 1];
            }
        }

        //完全展开（Full Unroll）
        //当循环的数目是固定值而且非常小时，即时编译器会将循环全部展开。
        //此时，原本循环中的循环判断语句将不复存在，取而代之的是若干个顺序执行的循环体
        {
            //未展开
            int[] a = new int[4];
            int sum = 0;
            for (int i = 0; i < 4; i++) {
                sum += a[i];
            }

            //完全展开
            sum += a[0];
            sum += a[1];
            sum += a[2];
            sum += a[3];
        }
    }
}
