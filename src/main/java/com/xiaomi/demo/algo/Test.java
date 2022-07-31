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


    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSourceName){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceName);
        return new TransactionTemplate(transactionManager);
    }

    public static void main(String[] args) throws IOException {
        //new Test().sortArray(new int[]{2,6,5});]]]
        //
        // 23121==> [2,3,1,2,1]
        // 从第一位开始找 第一个小于等于2的数 如果找到的是 2则继续，如果是小于2的数则不用找后面全部添加最大值即可
        //
        //  a-2  x-4
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
