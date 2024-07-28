package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(solveNQueens(4));
    }

    int[] idxs;
    private List<List<String>> ans = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        idxs = new int[n];
        dfs(0, n, new ArrayList<>());
        return ans;
    }

    private void dfs(int row, int n, List<String> list) {
        if (row == n) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int j = 0; j < n; j++) {
            if (isValid(row, j + 1)) {
                idxs[row] = j + 1;
                StringBuilder stb = new StringBuilder();
                for (int idx = 0; idx < n; idx++) {
                    if (idx == j) {
                        stb.append("Q");
                    } else {
                        stb.append(".");
                    }
                }
                list.add(stb.toString());
                dfs(row + 1, n, list);
                list.remove(list.size() - 1);
                idxs[row] = 0;
            }
        }
    }


    private boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (idxs[i] == col || (i - row == idxs[i] - col) || (i - row == col - idxs[i])) {
                return false;
            }
        }
        return true;
    }
}
