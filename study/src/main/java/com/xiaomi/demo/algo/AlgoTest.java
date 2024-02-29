package com.xiaomi.demo.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/24
 */
@Slf4j
public class AlgoTest {

    @Test
    public void test() {
        System.out.println(divide(-2147483648,2));
    }
    public int divide(int dividend, int divisor) {
        if(dividend==Integer.MIN_VALUE && divisor==-1){
            return Integer.MAX_VALUE;
        }
        if( divisor==1){
            return dividend;
        }
        if(divisor==-1){
            return -dividend;
        }
        if(dividend==divisor){
            return 1;
        }
        boolean isNegative = false;
        long dividendL= dividend;
        long divisorL = divisor;
        if(dividendL*divisorL<0){
            isNegative = true;
        }
        dividendL = Math.abs(dividendL);
        divisorL = Math.abs(divisorL);
        if(dividend<divisor || dividend==0 || divisor==Integer.MAX_VALUE || divisor==Integer.MIN_VALUE){
            return 0;
        }
        int ans = 1 ,temp = (int)divisorL;
        while((dividendL-divisorL)>=divisorL){
            ans+=ans;
            divisorL+=divisorL;
        }
        ans = ans+divide((int)(dividendL-divisorL),temp);
        return isNegative?-ans:ans;
    }
}