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
        int[][] intervals = new int[][]{
                {-52, 31},
                {-73, -26},
                {82, 97},
                {-65, -11},
                {-62, -49},
                {95, 99},
                {58, 95},
                {-31, 49},
                {66, 98},
                {-63, 2}, {30, 47}, {-40, -26}
        };

    }

    class MyCircularDeque {
        int[] arr;
        int cap;
        int front;
        int tail;

        public MyCircularDeque(int k) {
            arr = new int[k + 1];
            cap = k + 1;
            front = 0;
            tail = 0;
        }

        public boolean insertFront(int value) {
            if (isFull()) {
                return false;
            }
            front = (front - 1 + cap) % cap;
            arr[front] = value;
            return true;
        }

        public boolean insertLast(int value) {
            if (isFull()) {
                return false;
            }
            arr[tail] = value;
            tail = (tail + 1) % cap;
            return true;
        }

        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            front = (front + 1) % cap;
            return true;
        }

        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            tail = (tail - 1 + cap) % cap;
            return true;
        }

        public int getFront() {
            if (isEmpty()) {
                return -1;
            }
            return arr[front];
        }

        public int getRear() {
            if (isEmpty()) {
                return -1;
            }
            int idx = (tail - 1 + cap) % cap;
            return arr[idx];
        }

        public boolean isEmpty() {
            return tail == front;
        }

        public boolean isFull() {
            return front == (tail + 1) % cap;
        }
    }


    class MyCircularQueue {
        int[] arr;
        int cap;
        int front;
        int tail;

        public MyCircularQueue(int k) {
            arr = new int[k + 1];
            cap = k + 1;
            front = 0;
            tail = 0;
        }

        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            arr[tail] = value;
            tail = (tail + 1) % cap;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            front = (front + 1) % cap;
            return true;
        }

        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return arr[front];
        }

        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            int idx = (tail - 1 + cap) % cap;
            return arr[idx];
        }

        public boolean isEmpty() {
            return tail == front;
        }

        public boolean isFull() {
            return front == (tail + 1) % cap;
        }
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
