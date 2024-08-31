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
        int[] arr = new int[]{5, 2, 3, 1};
        System.out.println(Arrays.toString(quickSort(arr, 0, arr.length - 1)));
    }

    @Test
    public void testMergeSort() {
        int[] arr = new int[]{10, 8, 2, 6, 1, 2, 3, 4, 5, 7};
        System.out.println(Arrays.toString(mergeSort(arr, 0, arr.length - 1)));
    }

    @Test
    public void testHeapSort() {
        int[] arr = new int[]{10, 8, 2, 6, 1, 2, 3, 4, 5, 7};
        System.out.println(Arrays.toString(heapSort(arr)));
    }

    private int[] heapSort(int[] nums) {
        //这里元素的索引是从0开始的,所以最后一个非叶子结点array.length/2 - 1
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            adjustHeap(nums, i, nums.length);
        }

        // 上述逻辑，建堆结束
        // 下面，开始排序逻辑
        for (int j = nums.length - 1; j > 0; j--) {
            // 元素交换,作用是去掉大顶堆
            // 把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
            swap(nums, 0, j);
            // 元素交换之后，毫无疑问，最后一个元素无需再考虑排序问题了。
            // 接下来我们需要排序的，就是已经去掉了部分元素的堆了，这也是为什么此方法放在循环里的原因
            // 而这里，实质上是自上而下，自左向右进行调整的
            adjustHeap(nums, 0, j);
        }
        return nums;
    }


    private void adjustHeap(int[] nums, int i, int n) {
        int temp = nums[i];
        int k = i * 2 + 1;
        while (k < n) {
            if (k + 1 < n && nums[k + 1] > nums[k]) {
                k++;
            }
            if (nums[k] > temp) {
                nums[i] = nums[k];
                i = k;
                k = k * 2 + 1;
            } else {
                break;
            }
        }
        nums[i] = temp;
    }

    private int[] quickSort(int[] nums, int l, int r) {
        if (l >= r) {
            return nums;
        }
        int pivot = nums[l];
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
        nums[l] = nums[i];
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

    public void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
