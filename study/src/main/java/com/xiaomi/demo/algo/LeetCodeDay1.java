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
        System.out.println(garbageCollection(new String[]{"G", "P", "GP", "GG"}, new int[]{2, 4, 3}));
    }

    public int garbageCollection(String[] garbage, int[] travel) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < garbage.length; i++) {
            for (int j = 0; j < garbage[i].length(); j++) {
                char c = garbage[i].charAt(j);
                final int idx = i;
                if (c == 'M') {
                    map.computeIfAbsent('M', k -> {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(idx);
                        return list;
                    });
                } else if (c == 'P') {
                    map.computeIfAbsent('P', k -> {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(idx);
                        return list;
                    });
                } else {
                    map.computeIfAbsent('G', k -> {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(idx);
                        return list;
                    });
                }
            }
        }
        int[] prefix = new int[travel.length + 1];
        for (int i = 1; i <= travel.length; i++) {
            prefix[i] = prefix[i - 1] + travel[i - 1];
        }
        int ans = 0;
        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
            for (int i : entry.getValue()) {
                for (char c : garbage[i].toCharArray()) {
                    if (c == entry.getKey()) {
                        ans++;
                    }
                }
            }
            ans += prefix[entry.getValue().get(entry.getValue().size() - 1)];
        }
        return ans;
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
