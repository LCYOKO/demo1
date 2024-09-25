package com.xiaomi.demo.algo;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;

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


    @org.junit.Test
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

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        if (dividend < divisor || dividend == 0 || divisor == Integer.MAX_VALUE || divisor == Integer.MIN_VALUE) {
            return 0;
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
        boolean isNegative = false;
        long dividendL = dividend;
        long divisorL = divisor;
        if (dividendL * divisorL < 0) {
            isNegative = true;
        }
        dividendL = Math.abs(dividendL);
        divisorL = Math.abs(divisorL);
        int ans = 1, temp = (int) divisorL;
        while ((dividendL - divisorL) >= divisorL) {
            ans += ans;
            divisorL += divisorL;
        }
        ans = ans + divide((int) (dividendL - divisorL), temp);
        return isNegative ? -ans : ans;
    }

    @Test
    public void test2() {
    }
}
