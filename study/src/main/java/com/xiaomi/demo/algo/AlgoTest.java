package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/24
 */
@Slf4j
public class AlgoTest {

    @Test
    public void test12() {
        AtomicInteger atomicInteger = new AtomicInteger(Integer.MAX_VALUE);
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.getAndIncrement());
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
    public void test() {
        trapRainWater(new int[][]{
                {1, 4, 3, 1, 3, 2},
                {3, 2, 1, 3, 2, 4},
                {2, 3, 3, 2, 3, 1}
        });
    }


    int[][] dirs = new int[][]{
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    public int trapRainWater(int[][] heightMap) {
        int n = heightMap.length;
        if (n <= 2) {
            return 0;
        }
        int m = heightMap[0].length;
        if (m <= 2) {
            return 0;
        }

        PriorityQueue<int[]> que = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    que.add(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true;
                }
            }
        }
        int ans = 0;
        while (!que.isEmpty()) {
            int[] dot = que.poll();
            int x = dot[0], y = dot[1], h = dot[2];
            for (int[] dir : dirs) {
                int nX = x + dir[0];
                int nY = y + dir[1];
                if (nX < 0 || nX >= n || nY < 0 || nY >= m || visited[nX][nY]) {
                    continue;
                }
                if (heightMap[nX][nY] < h) {
                    ans += h - heightMap[nX][nY];
                }
                que.add(new int[]{nX, nY, heightMap[nX][nY]});
                visited[nX][nY] = true;
            }
        }
        return ans;
    }


    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (dp[i][j] == 0) {
                    dp[i][j] = Integer.MAX_VALUE;
                }
                int coin = coins[i - 1];
                if (j >= coin && dp[i - 1][j - coin] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - coin] + 1);
                }
            }
        }
        return dp[n][amount] == Integer.MAX_VALUE ? -1 : dp[n][amount];
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1];
        for (int i = 1; i <= len; i++) {
            int zeroCnt = 0;
            int oneCnt = 0;
            for (char c : strs[i - 1].toCharArray()) {
                if (c == '1') {
                    oneCnt++;
                } else {
                    zeroCnt++;
                }
            }
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    if (zeroCnt <= j && oneCnt <= k) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zeroCnt][k - oneCnt] + 1);
                    } else {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }
                }
            }
        }
        return dp[n][m][n];
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
        boolean isNegative = false;
        long dividendL = dividend;
        long divisorL = divisor;
        if (dividendL * divisorL < 0) {
            isNegative = true;
        }
        dividendL = Math.abs(dividendL);
        divisorL = Math.abs(divisorL);
        if (dividend < divisor || dividend == 0 || divisor == Integer.MAX_VALUE || divisor == Integer.MIN_VALUE) {
            return 0;
        }
        int ans = 1, temp = (int) divisorL;
        while ((dividendL - divisorL) >= divisorL) {
            ans += ans;
            divisorL += divisorL;
        }
        ans = ans + divide((int) (dividendL - divisorL), temp);
        return isNegative ? -ans : ans;
    }


    @Test
    public void test1() {
    }
}