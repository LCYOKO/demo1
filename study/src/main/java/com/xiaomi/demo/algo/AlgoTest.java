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
    public void combine() {
        dfs(1, 4, 2, new ArrayList<>());
    }

    private void dfs(int cur, int n, int k, List<Integer> list) {
        if (cur > n) {
            return;
        }
        if (list.size() == k) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int i = cur; i <= n; i++) {
            list.add(i);
            dfs(i + 1, n, k, list);
            list.remove(list.size() - 1);
        }
    }
}
