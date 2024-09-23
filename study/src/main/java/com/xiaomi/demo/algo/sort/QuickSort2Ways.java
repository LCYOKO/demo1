package com.xiaomi.demo.algo.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class QuickSort2Ways {
    Random random = new Random();

    private int partition(int[] arr, int l, int r) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int randomIndex = random.nextInt(r - l + 1) + l;
        swap(arr, l, randomIndex);
        int v = arr[l];
        int i = l + 1;
        int j = r;
        while (i <= j) {
            while (i <= r && arr[i] < v) {
                i++;
            }
            while (j >= l + 1 && arr[j] > v) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, l, j);
        return j;
    }

    private void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(arr, l, r);
        sort(arr, l, p - 1);
        sort(arr, p + 1, r);
    }

    private void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    @Test
    public void test() {
        int[] arr = new int[]{1, 3, 5, 7, 7, 7, 2, 1, 9, 2, 4, 6, 8, 0};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}