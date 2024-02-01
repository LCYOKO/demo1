package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 * https://leetcode.cn/problems/sort-an-array/solutions/178305/pai-xu-shu-zu-by-leetcode-solution/
 */
@Slf4j
public class ArraySortTest {

    @Test
    public void testQuickSort() {

    }

//    private int[] quickSort(int[] nums, int l, int r) {
//        if (l >= r) {
//            return nums;
//        }
//        int pivot = nums[l];
//        while (l < r) {
//
//        }
//
//    }

    @Test
    public void testMergeSort() {
        int[] arr = new int[]{10, 8, 6, 1, 2, 3, 4, 5, 7};
        System.out.println(Arrays.toString(mergeSort(arr, 0, arr.length - 1)));
    }

    private int[] mergeSort(int[] arr, int l, int r) {
        if (l >= r) {
            return arr;
        }
        int mid = (r - l) / 2 + l;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        return merge(arr, l, mid, mid + 1, r);
    }

    private int[] merge(int[] arr, int ll, int lr, int rl, int rr) {
        int l1 = ll;
        int l2 = rl;
        int[] temp = new int[arr.length];
        int idx = 0;
        while (l1 <= lr && l2 <= rr) {
            if (arr[l1] > arr[l2]) {
                temp[idx++] = arr[l2++];
            } else {
                temp[idx++] = arr[l1++];
            }
        }
        while (l1 <= lr) {
            temp[idx++] = arr[l1++];
        }
        while (l2 <= rr) {
            temp[idx++] = arr[l2++];
        }
        idx = 0;
        l1 = ll;
        while (l1 <= rr) {
            arr[l1++] = temp[idx++];
        }
        return arr;
    }

    private String loadFunc() {
        log.info("execute loadFunc");
        return "test";
    }
}
