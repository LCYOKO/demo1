package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 */
@Slf4j
public class ArraySortTest {

    @Test
    public void testArraySort() {
        Integer[] arr = new Integer[]{10, 8, 6, 1, 2, 3, 4, 5, 7};
        Arrays.sort(arr, (o1, o2) -> {
            return o2 - o1;
        });
        System.out.println(Arrays.toString(arr));
    }


    @Test
    public void testMergeSort() {
        int[] arr = new int[]{10, 8, 6, 1, 2, 3, 4, 5, 7};

    }

//    private int[] mergeSort(int[] arr, int l, int r) {
//        if (l == r) {
//
//        }
//
//    }

    private String loadFunc() {
        log.info("execute loadFunc");
        return "test";
    }
}
