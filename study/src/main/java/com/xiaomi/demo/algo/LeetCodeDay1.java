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

    public int largestRectangleArea(int[] heights) {
        int ans = 0;
        for (int i = 0; i < heights.length; i++) {
            int left = i;
            int min = heights[i];
            while (left < heights.length) {
                min = Math.min(min, heights[left]);
                left++;
            }
            ans = Math.max(ans, min * (left - i));
        }
        return ans;
    }

    public int largestRectangleArea1(int[] heights) {
        int ans = 0;
        Deque<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            while (!que.isEmpty() && heights[que.peekLast()] > heights[i]) {

            }
        }
        return ans;
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
