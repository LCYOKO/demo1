package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/24
 */
@Slf4j
public class AlgoTest {
    List<List<Integer>> ans = new ArrayList<>();

    @Test
    public void test() {
        lengthOfLIS(new int[]{});
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = 1;
            for (int j = i; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                } else {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
        }
        return dp[n];
    }
}
