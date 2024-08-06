package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        System.out.println(verifyTreeOrder(new int[]{4, 8, 6, 12, 16, 14, 10}));
    }

    public boolean verifyTreeOrder(int[] postorder) {
        return check(postorder, 0, postorder.length - 1, Integer.MAX_VALUE, true);
    }

    private boolean check(int[] postorder, int l, int r, int preVal, boolean left) {
        if (l > r) {
            return true;
        }
        int root = postorder[r];

        int lIdx = -1;
        for (int i = r; i >= l; i--) {
            if (left && postorder[i] > preVal) {
                return false;
            }
            if (!left && postorder[i] < preVal) {
                return false;
            }
            if (postorder[i] < root && lIdx == -1) {
                lIdx = i;
            }
        }
        return check(postorder, l, lIdx, root, true) && check(postorder, lIdx == -1 ? l : lIdx + 1, r - 1, root, false);
    }

}
