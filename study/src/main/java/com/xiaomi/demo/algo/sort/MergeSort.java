package com.xiaomi.demo.algo.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Author liuchiyun
 * @Date 2024/9/22
 */
public class MergeSort {
    private void merge(int[] arr, int l, int mid, int r) {
        int[] temp = new int[arr.length];
        int i = l;
        int j = mid + 1;
        int k = l;
        while (i <= mid && j <= r) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= r) {
            temp[k++] = arr[j++];
        }
        for (k = l; k <= r; k++) {
            arr[k] = temp[k];
        }
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, l, mid, r);
        }
    }

    @Test
    public void test() {
        int[] arr = new int[]{1, 3, 5, 7, 7, 7, 2, 1, 9, 2, 4, 6, 8, 0};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
