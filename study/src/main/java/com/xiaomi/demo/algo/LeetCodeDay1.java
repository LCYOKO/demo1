package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/20
 */
@Slf4j
public class LeetCodeDay1 {

    /**
     * len = len1+len2;
     * <p>
     * x =  len1+ y
     * 2len1+2y = len1+n*len2+y;
     * len1 = n*len2-y;
     */
    @Test
    public void test() {
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

    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n < 2) {
            return n;
        }
        Map<String, Set<String>> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];
                String key = "";
                if (x == 0) {
                    key = points[i][0] + "-" + 0;
                } else if (y == 0) {
                    key = 0 + "-" + points[i][1];
                } else {
                    if (x < 0 && y < 0) {
                        x = -x;
                        y = -y;
                    }
                    if (y < 0 && x > 0) {
                        x = -x;
                        y = -y;
                    }
                    boolean xIsNegative = false;
                    if (x < 0) {
                        x = -x;
                        xIsNegative = true;
                    }
                    int gcd = gcd(x, y);
                    key = (xIsNegative ? -x : x) / gcd + "-" + y / gcd;
                }
                if (!map.containsKey(key)) {
                    map.put(key, new HashSet<>());
                }
                map.get(key).add(getPoint(points[i]));
                map.get(key).add(getPoint(points[j]));
                ans = Math.max(ans, map.get(key).size());
            }
        }
        return ans;
    }

    private String getPoint(int[] point) {
        return point[0] + "_" + point[1];
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
