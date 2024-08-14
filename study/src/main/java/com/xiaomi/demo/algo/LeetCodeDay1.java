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
    }

//    private void quickSort(int[] nums, int l, int r) {
//        if (l >= r) {
//            return;
//        }
//        int mid = (r - l) / 2 + l;
//        int i = l;
//        int j = r;
//        while (i < j) {
//            while (i < j && nums[j] >= nums[mid]) {
//                j--;
//            }
//            while (i < j && nums[i] <= nums[mid]) {
//                i++;
//            }
//            if (i < j) {
//                int temp = nums[i];
//                nums[i] = nums[j];
//                nums[j] = temp;
//                i++;
//                j--;
//            }
//        }
//        int temp = nums[mid];
//        nums[mid] = nums[i];
//        nums[i] = temp;
//        quickSort(nums, l, i);
//        quickSort(nums, i + 1, r);
//    }

}
