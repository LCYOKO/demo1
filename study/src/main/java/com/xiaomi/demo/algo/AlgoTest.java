package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/24
 */
@Slf4j
public class AlgoTest {

    @Test
    public void testNums(){
        System.out.println(firstMissingPositive(new int[]{1,2}));
    }
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for(int i=0;i<nums.length;){
            if(nums[i]<=0 || nums[i]>n || nums[nums[i]-1]==i+1){
                i++;
                continue;
            }
            if(nums[nums[i]-1]!=nums[i]){
                int temp = nums[i];
                nums[i] = nums[temp-1];
                nums[temp-1] = temp;
            } else {
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));
        for(int i=0;i<n;i++){
            if(nums[i]<=0 || nums[i]>n || nums[nums[i]-1]!=i+1){
                return i+1;
            }
        }
        return n+1;
    }

    @Test
    public void testAlgo() {
        solveSudoku(new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        });
    }

    int[][] cols = new int[9][9];
    int[][] rows = new int[9][9];
    int[][] tables = new int[9][9];
    List<int[]> poss = new ArrayList<>();
    boolean valid = false;

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int curTableIdx = (i / 3) * 3 + j / 3;
                    int k = board[i][j] - '1';
                    cols[j][k] = 1;
                    rows[i][k] = 1;
                    tables[curTableIdx][k] = 1;
                } else {
                    poss.add(new int[]{i, j});
                }
            }
        }
        dfs(0, board);
        for (char c[] : board) {
            System.out.println(Arrays.toString(c));
        }
    }

    private void dfs(int pos, char[][] board) {
        if (pos == poss.size()) {
            valid = true;
            return;
        }
        int i = poss.get(pos)[0];
        int j = poss.get(pos)[1];
        int curTableIdx = (i / 3) * 3 + j / 3;
        for (int k = 0; k < 9 && !valid; k++) {
            if (cols[j][k] != 0 || rows[i][k] != 0 || tables[curTableIdx][k] != 0) {
                continue;
            }
            cols[j][k] = 1;
            rows[i][k] = 1;
            tables[curTableIdx][k] = 1;
            board[i][j] = (char) ('0' + k);
            dfs(pos + 1, board);
            cols[j][k] = 0;
            rows[i][k] = 0;
            tables[curTableIdx][k] = 0;
            board[i][j] = '.';
        }
    }
}