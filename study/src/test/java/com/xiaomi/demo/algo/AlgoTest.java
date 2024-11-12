package com.xiaomi.demo.algo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/3/7
 */
public class AlgoTest {

    @Test
    @DisplayName("测试1")
    public void test1() {
        Locale locale = Locale.forLanguageTag("zh_CN");
        Assert.assertNotNull(locale);
    }


    @Test
    public void testCircleSteps() {
        System.out.println(circleSteps(10, 2));
    }

    /**
     * 圆环上有10个点，编号为0~9。从0点出发，每次可以逆时针和顺时针走一步，问走n步回到0点共有多少种走法。
     * 输入: 2
     * 输出: 2
     * 解释：有2种方案。分别是0->1->0和0->9->0
     */
    public int circleSteps(int n, int k) {
        // 圆环中有n个节点，走k步回答原点有几种走法
        // 走k步走到0的走法=走k-1步走到1的走法 + 走k-1步走到num-1的走法
        // dp[i][j]表示走i步走到j点的走法种类
        // dp[i][j] = dp[i-1][(j+1)%len] + dp[i-1][(j-1+len)%len]
        int[][] dp = new int[k + 1][n];
        dp[0][0] = 1;
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = dp[i - 1][(j + 1) % n] + dp[i - 1][(j - 1 + n) % n];
            }
        }
        return dp[k][0];
    }

    @Test
    public void test2() throws InterruptedException {
        System.out.println(fractionToDecimal(1, 3));
    }


    public int countPrimes(int n) {
        //解答 为什么从x*x开始 为什么到 sqrt（x）就停止。
        //
        //假如我们要判断9是不是质数。
        //
        //如果它不是质数，一定有两个数相乘等于它。这两个因数一定有一个因数小于等于根号9。也就是3。
        //
        //那么反过来，我们已经把比3小的数的所有的倍数都排除了。
        //
        //那么对于3到9之间的数，比如7 它如果不是质数，它的一个因子应该是小于3。但是小于3的数的所有倍数都被我们排除了。所以当我们验证到3的倍数的时候，最小的倍数就是3的平方。
        //
        //再举一个3-9之间的数 6，j+=i
        //
        //j=4 i=2 在第二次循环的时候已经被排除了。
        //
        //因此x到 x*x之间留下的数都不用再判断啦
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        // 从 2 开始枚举到 sqrt(n)。
        for (int i = 2; i * i < n; i++) {
            // 如果当前是素数
            if (isPrim[i]) {
                // 就把从 i*i 开始，i 的所有倍数都设置为 false。
                for (int j = i * i; j < n; j+=i) {
                    isPrim[j] = false;
                }
            }
        }

        // 计数
        int cnt = 0;
        for (int i = 2; i < n; i++) {
            if (isPrim[i]) {
                cnt++;
            }
        }
        return cnt;
    }

    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == Integer.MIN_VALUE && denominator == -1) {
            return String.valueOf(Integer.MIN_VALUE*-1L);
        }
        if (numerator == 0) {
            return "0";
        }
        if (numerator % denominator == 0) {
            return String.valueOf(numerator / denominator);
        }
        long num = numerator;
        long den = denominator;
        StringBuilder stb = new StringBuilder();
        if (num * den < 0) {
            stb.append('-');
        }
        num = Math.abs(num);
        den = Math.abs(den);
        stb.append(num / den).append('.');
        num = num % den;
        Map<Long, Integer> remain2IdxMap = new HashMap<>();
        while (num != 0) {
            remain2IdxMap.put(num, stb.length());
            num *= 10;
            stb.append(num / den);
            num = num % den;
            if (remain2IdxMap.containsKey(num)) {
                int idx = remain2IdxMap.get(num);
                return String.format("%s(%s)", stb.substring(0, idx), stb.substring(idx));
            }
        }
        return stb.toString();
    }

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        if (divisor == 1) {
            return dividend;
        }
        if (divisor == -1) {
            return -dividend;
        }
        if (dividend == divisor) {
            return 1;
        }
        if (dividend == 0) {
            return 0;
        }
        long dividendL = dividend;
        long divisorL = divisor;
        boolean isNegative = false;
        if (dividendL * divisorL < 0) {
            isNegative = true;
        }
        dividendL = Math.abs(dividendL);
        divisorL = Math.abs(divisorL);
        if (dividendL < divisorL) {
            return 0;
        }
        int ans = 1;
        long temp = divisorL;
        while ((dividendL - divisorL) >= divisorL) {
            ans += ans;
            divisorL += divisorL;
        }
        ans += divide((int) (dividendL - divisorL), (int) temp);
        return isNegative ? -ans : ans;
    }
}
