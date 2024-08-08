package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int k1 = (nums1.length + nums2.length + 1) / 2;
        int k2 = (nums1.length + nums2.length + 2) / 2;

        return (findKth(nums1, 0, len1 - 1, nums2, 0, len2 - 1, k1) + findKth(nums1, 0, len1 - 1, nums2, 0, len2 - 1, k2)) / 2.0;
    }

    private int findKth(int[] nums1, int l1, int r1, int[] nums2, int l2, int r2, int k) {
        if (l1 > r1) {
            return nums2[l2 + k - 1];
        }
        if (l2 > r2) {
            return nums1[l1 + k - 1];
        }
        if (k == 1) {
            return Math.min(nums1[l1], nums2[l2]);
        }
        int idx1 = l1 + k / 2 > r1 ? r1 : l1 + k / 2;
        int idx2 = l2 + k / 2 > r2 ? r2 : l2 + k / 2;

        if (nums1[idx1] < nums2[idx2]) {
            return findKth(nums1, idx1, r1, nums2, l2, r2, k - (idx1 - l1));
        } else {
            return findKth(nums1, l1, r1, nums2, idx2, r2, k - (idx2 - l2));
        }
    }
}
