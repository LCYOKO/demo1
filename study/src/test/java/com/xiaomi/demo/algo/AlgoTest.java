package com.xiaomi.demo.algo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.PriorityQueue;

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


    public int calculate(String str) {
        Deque<String> expression = buildExpression(str);
        Deque<Integer> nums = new ArrayDeque<>();
        for (String s : expression) {
            if (s.equals("+")) {
                int num1 = nums.pollLast();
                int num2 = nums.pollLast();
                nums.addLast(num2 + num1);
            } else if (s.equals("-")) {
                int num1 = nums.pollLast();
                int num2 = nums.pollLast();
                nums.addLast(num2 - num1);

            } else if (s.equals("*")) {
                int num1 = nums.pollLast();
                int num2 = nums.pollLast();
                nums.addLast(num2 * num1);
            } else if (s.equals("/")) {
                int num1 = nums.pollLast();
                int num2 = nums.pollLast();
                nums.addLast(num2 / num1);
            } else {
                nums.addLast(Integer.parseInt(s));
            }
        }
        int ans = 0;
        while (!nums.isEmpty()) {
            ans += nums.pollLast();
        }
        return ans;
    }

    private Deque<String> buildExpression(String s) {
        Deque<String> expression = new ArrayDeque<>();
        Deque<String> ops = new ArrayDeque<>();
        StringBuilder num = new StringBuilder();
        char pre = ' ';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c == '+' || c == '-') {
                if (c == '-' && (pre < '0' || pre > '9')) {
                    expression.addLast("0");
                }
                if (num.length() > 0) {
                    expression.addLast(num.toString());
                    num = new StringBuilder();
                }
                while (!ops.isEmpty() && !"(".equals(ops.peekLast())) {
                    expression.addLast(ops.pollLast());
                }
                ops.addLast("" + c);
            } else if (c == '*' || c == '/') {
                if (num.length() > 0) {
                    expression.addLast(num.toString());
                    num = new StringBuilder();
                }
                while (!ops.isEmpty() && ("*".equals(ops.peekLast()) || "/".equals(ops.peekLast())) && !"(".equals(ops.peekLast())) {
                    expression.addLast(ops.pollLast());
                }
                ops.addLast("" + c);
            } else if (c == '(') {
                ops.addLast("(");
            } else if (c == ')') {
                if (num.length() > 0) {
                    expression.addLast(num.toString());
                    num = new StringBuilder();
                }
                while (!ops.isEmpty() && !"(".equals(ops.peekLast())) {
                    expression.addLast(ops.pollLast());
                }
                ops.pollLast();
            } else {
                num.append(c);
            }
            pre = c;
        }
        if (num.length() > 0) {
            expression.addLast(num.toString());
        }
        while (!ops.isEmpty()) {
            expression.addLast(ops.pollLast());
        }
        return expression;
    }

    @Test
    public void test2() {
        findMaximizedCapital(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1});
    }


    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        for (int i = 0; i < profits.length; i++) {
            minHeap.offer(new int[]{capital[i], profits[i]});
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        while (k > 0) {
            while (!minHeap.isEmpty() && minHeap.peek()[0] <= w) {
                int[] arr = minHeap.poll();
                maxHeap.add(arr[1]);
            }
            if (maxHeap.isEmpty()) {
                break;
            }
            w += maxHeap.poll();
            k--;
        }
        return w;
    }


    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int trapRainWater(int[][] heightMap) {
        int n = heightMap.length;
        if (n <= 2) {
            return 0;
        }
        int m = heightMap[0].length;
        if (m <= 2) {
            return 0;
        }
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{i, 0, heightMap[i][0]});
            queue.offer(new int[]{i, m - 1, heightMap[i][m - 1]});
            visited[i][0] = true;
            visited[i][m - 1] = true;
        }

        for (int j = 1; j < m - 1; j++) {
            queue.offer(new int[]{0, j, heightMap[0][j]});
            queue.offer(new int[]{n - 1, j, heightMap[n - 1][j]});
            visited[0][j] = true;
            visited[n - 1][j] = true;
        }

        int ans = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1], h = cur[2];
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m || visited[nx][ny]) {
                    continue;
                }
                visited[nx][ny] = true;
                if (heightMap[nx][ny] < h) {
                    ans += h - heightMap[nx][ny];
                }
                queue.offer(new int[]{nx, ny, Math.max(heightMap[nx][ny], h)});
            }
        }
        return ans;
    }
}
