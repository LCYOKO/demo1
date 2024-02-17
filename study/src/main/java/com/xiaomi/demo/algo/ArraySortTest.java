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
        int[] arr = new int[]{5,2,3,1};
        System.out.println(Arrays.toString(quickSort(arr, 0, arr.length - 1)));
    }

    @Test
    public void testMergeSort() {
        int[] arr = new int[]{10, 8, 2, 6, 1, 2, 3, 4, 5, 7};
        System.out.println(Arrays.toString(mergeSort(arr, 0, arr.length - 1)));
    }

    @Test
    public void testHeapSort() {

    }

    private int[] heapSort(int[] nums) {
        return nums;
    }

    private void heapfy(int[] nums) {

    }

    private void adjust(int[] nums) {

    }

    private int[] quickSort(int[] nums, int l, int r) {
        if (l >= r) {
            return nums;
        }
        int mid = (r - l) / 2 + l;
        int pivot = nums[mid];
        int i = l, j = r;
        while (i < j) {
            while (i < j && nums[j] >= pivot) {
                j--;
            }
            while (i < j && nums[i] <= pivot) {
                i++;
            }
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        nums[mid] = nums[i];
        nums[i] = pivot;
        quickSort(nums, l, i - 1);
        quickSort(nums, i + 1, r);
        return nums;
    }

    private int[] mergeSort(int[] arr, int l, int r) {
        if (l >= r) {
            return arr;
        }
        int mid = (r - l) / 2 + l;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        int l1 = l;
        int l2 = mid + 1;
        int[] temp = new int[arr.length];
        int idx = 0;
        while (l1 <= mid && l2 <= r) {
            if (arr[l1] > arr[l2]) {
                temp[idx++] = arr[l2++];
            } else {
                temp[idx++] = arr[l1++];
            }
        }
        while (l1 <= mid) {
            temp[idx++] = arr[l1++];
        }
        while (l2 <= r) {
            temp[idx++] = arr[l2++];
        }
        idx = 0;
        l1 = l;
        while (l1 <= r) {
            arr[l1++] = temp[idx++];
        }
        return arr;
    }
}
