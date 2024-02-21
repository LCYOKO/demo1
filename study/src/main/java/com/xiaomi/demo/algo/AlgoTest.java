package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/24
 */
@Slf4j
public class AlgoTest {

    @Test
    public void test() {
        System.out.println(findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4, 5, 6}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int kth1 = (n + m + 1) / 2;
        int kth2 = (n + m + 2) / 2;
        System.out.println(kth1 + "___" + kth2);
        return (findKth(nums1, 0, n - 1, nums2, 0, m - 1, kth1) + findKth(nums1, 0, n - 1, nums2, 0, m - 1, kth2)) / 2.0;
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
        int subLen = k / 2;
        int idx1 = l1 + subLen > r1 ? r1 : l1 + subLen - 1;
        int idx2 = l2 + subLen > r2 ? r2 : l2 + subLen - 1;
        if (nums1[idx1] < nums2[idx2]) {
            return findKth(nums1, idx1+1, r1, nums2, l2, r2, k-(idx1-l1+1));
        } else {
            return findKth(nums1, l1, r1, nums2, idx2+1, r2, k -(idx2-l2+1));
        }
    }
}