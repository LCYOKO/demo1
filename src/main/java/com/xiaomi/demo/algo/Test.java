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
 * @Version 1.0
 */
public class Test {


    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSourceName){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceName);
        return new TransactionTemplate(transactionManager);
    }

    public static void main(String[] args) throws IOException {
        //@TthritField
        //new Test().sortArray(new int[]{2,6,5});]]]
        //  y/z=3
        // x/y=3    x=3y      3y=2a=4b    3y=4b;
        // a/b=2    a=2b

        //
        // 23121==> [2,3,1,2,1]
        // 从第一位开始找 第一个小于等于2的数 如果找到的是 2则继续，如果是小于2的数则不用找后面全部添加最大值即可
        //
        //  a-2  x-4
    }


    static  Map<String,Integer> process(String str){
        Map<String,Integer> map =new HashMap<>();
        if(str ==null || str.length()==0){
            return map;
        }
        String strs[] = str.split(",");
        System.out.println(Arrays.toString(strs));
        for(String str1:strs){
            String strs2[] = str1.split(":");
            map.put(strs2[0],map.getOrDefault(strs[0],0)+Integer.parseInt(strs[1]));
        }
        return map;
    }


    @lombok.Data
    @Builder
   public static  class Data{
        private String name;
        private Double age;
   }

    public int minArea(char[][] image, int x, int y) {
        int n = image.length, m = image[0].length;
        int x1 = n, y1 = n, x2 = -1, y2 = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (image[i][j] == 1) {
                    if (x1 > i || y1 < j) {
                        x1 = i;
                        y1 = j;
                    }
                    if (i > x2 || y2 > j) {
                        x2 = i;
                        y2 = j;
                    }
                    System.out.println(x1 + "_" + y1 + "_" + x2 + "_" + y2);
                }
            }
        }
        return x1 == n ? 0 : (x2 - x1) * (y2 - y1);
    }

    public int[] sortArray(int[] nums) {
        int n=nums.length;
        for(int i=n/2-1;i>=0;i--){
            adjust(n,i,nums);
        }
        for(int i=n-1;i>=0;i--){
            System.out.println(Arrays.toString(nums));
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
        System.out.println(maxIdx+"_"+i);
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
