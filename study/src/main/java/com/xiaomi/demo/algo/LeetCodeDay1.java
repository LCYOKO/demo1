package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        int[] nums = new int[]{3, 30, 34, 5, 9};
        Arrays.stream(nums).mapToObj(Integer::toString).sorted((o1, o2) -> {
            String num1 = o1 + o2;
            String num2 = o2 + o1;
            return num2.compareTo(num1);
        }).collect(Collectors.joining());
    }

    public String largestNumber(int[] nums) {
        String str = Arrays.stream(nums).mapToObj(Integer::toString).sorted((o1, o2) -> {
            String num1 = o1 + o2;
            String num2 = o2 + o1;
            return num2.compareTo(num1);
        }).collect(Collectors.joining());
        if (str.length() == 0) {
            return str;
        }
        if (str.charAt(0) == '0') {
            return "0";
        }
        return str;
    }
}
