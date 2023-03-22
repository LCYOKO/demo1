package com.xiaomi.demo.algo;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import sun.nio.ch.DirectBuffer;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 */
public class Test {


    public native void  sayHello();


    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSourceName){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceName);
        return new TransactionTemplate(transactionManager);
    }


    public static void process(int arr[]) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int l = 0, r = 0, n = arr.length;
        int start =0, end =0;
        long max = -1, cur = 1;
        while (r < n) {
            if (arr[r]!=0){
                cur*=Math.log(arr[r])/Math.log(2)+1;
            }
            if (cur > max) {
                max = cur;
                start = l;
                end = r;
            }
            if (arr[r] == 0) {
                l = r + 1;
                cur=1;
            }
            r++;
        }
     System.out.println((start+1)+" "+(end+1));
    }


    public static double  prcoess()  {
        System.out.println(Double.MAX_VALUE);
        int n=2,m=1;
        double pi[] = new double[]{89,38};
        double score[] = new double[]{445,754};
        double min = 0;
        double dp[][] = new double[n+1][m+1];
        for(int i=0;i<n;i++){
            min+=pi[i]*score[i];
            dp[i+1][0]=pi[i]*score[i];
        }
        if(m==0){
            return min;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                dp[i][j] = Math.max(dp[i-1][m]+score[i-1]*pi[i-1],dp[i-1][m-1]+score[i-1]*100);
            }
        }
        return dp[n][m]/100;
    }
   static Map<String,Integer> dp  = new HashMap<>();
    public static int process(String str1, String str2){
        if(str1.length()==0 || str2.length()==0){
            return -1;
        }
        int num1  = Integer.parseInt(str1);
        int num2 = Integer.parseInt(str2);
        if (num1==1 || num1==0 || num2==1 || num2==0 || num1%num2==0 || num2%num1==0){
            return 0;
        }
        if(dp.containsKey(str1+str2)){
            return dp.get(str1+str2);
        }
        int min=-1;
        for(int i=0;i<str1.length();i++){
            int val =  process(getStr(str1,i),str2);
            if(val!=-1){
                if (min==-1) min=val;
                min = Math.min(min,val);
            }
        }

        for(int i=0;i<str2.length();i++){
            int val =  process(str1,getStr(str2,i));
            if(val!=-1){
                if (min==-1) min=val;
                else min = Math.min(min,val);
            }
        }
        if (min==-1){
            return min;
        }
        dp.put(str1+str2,min+1);
        return min+1;
    }

    private static String getStr(String str,int idx){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str.length();i++){
            if(i!=idx) sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        process(new int[]{1,2,4,0,8});
//    System.out.println(Math.log(0)/Math.log(2));
    }
    public int[] sortArray(int[] nums) {
        int n=nums.length;
        for(int i=n/2-1;i>=0;i--){
            adjust(n,i,nums);
        }
        for(int i=n-1;i>=0;i--){
            swap(nums,i,0);
            adjust(i,0,nums);
        }
        return nums;
    }

    private void adjust(int n,int i,int nums[]){
        int l =i*2;
        int r= i*2+1;
        int maxIdx=i;
        if(l<n && nums[l]>nums[maxIdx]){
            maxIdx=l;
        }
        if(r<n && nums[r]>nums[maxIdx]){
            maxIdx=r;
        }
        if(maxIdx!=i){
            swap(nums,i,maxIdx);
            adjust(n,maxIdx,nums);
        }
    }

    void swap(int nums[],int i,int j){
        int t =nums[i];
        nums[i]=nums[j];
        nums[j]=t;
    }
}
