package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

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
}
