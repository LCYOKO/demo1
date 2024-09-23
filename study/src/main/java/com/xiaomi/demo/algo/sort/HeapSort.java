package com.xiaomi.demo.algo.sort;

import org.junit.Test;

import java.util.Arrays;

public class HeapSort {

    @Test
    public void test() {
        int[] arr = new int[]{1, 3, 5, 7, 7, 7, 2, 1, 9, 2, 4, 6, 8, 0};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public void sort(int[] arr) {
        heapfy(arr);
        int len = arr.length;
        for (int i = len - 1; i >= 0; i--) {
            swap(arr, 0, i);
            adjust(arr, i, 0);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
    private void heapfy(int[] arr) {
        int len = arr.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            adjust(arr, arr.length, i);
        }
    }

    private void adjust(int[] arr, int len, int i) {
        int val = arr[i];
        int left = 2 * i + 1;
        while (left < len) {
            //左右子节点取最大的
            if (left + 1 < len && arr[left + 1] > arr[left]) {
                left += 1;
            }
            if (val >= arr[left]) {
                break;
            }
            arr[i] = arr[left];
            i = left;
            left = 2 * i + 1;
        }
        arr[i] = val;
    }
}