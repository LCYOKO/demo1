package com.xiaomi.demo.net;

import io.netty.channel.epoll.EpollSocketChannel;
import org.junit.Test;
import org.springframework.data.relational.core.sql.In;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class TestIO {

    @Test
    public void test() throws FileNotFoundException {
//
       firstMissingPositive(new int[]{-1,1,2,0,4});
    }


    public boolean isConvex(List<List<Integer>> points) {
        boolean flag=false;
        int n=points.size();
        if(!compl(points.get(n-1),points.get(0),points.get(1))){
            return false;
        }
        if(!compl(points.get(n-2),points.get(n-1),points.get(1))){
            return false;
        }
        for(int i=2;i<n;i++){
            if(!compl(points.get(i-2),points.get(i-1),points.get(i))){
                return false;
            }
        }
        return true;
    }

    private boolean compl(List<Integer> p1, List<Integer> p2, List<Integer> p3){
        int x1=p1.get(0)-p2.get(0),y1=p1.get(1)-p2.get(1),x2=p3.get(0)-p2.get(0),y2=p3.get(1)-p2.get(1);
        return  x1*x2+y1*y2>=0?true:false;
    }

    public int firstMissingPositive(int[] nums) {
        int n=nums.length;
        for(int i=0;i<n;){
            if(nums[i]<0 || nums[i]>=n || nums[i]==i){
                i++;
                continue;
            }
            if(nums[nums[i]]!=nums[i]){
                swap(nums,nums[i],i);
            }
            else i++;
        }
        System.out.println(Arrays.toString(nums));
        for(int i=1;i<n;i++){
            if(nums[i]!=i){
                return  i;
            }
        }
        return  n;
    }

    private void swap(int nums[],int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }

    class TreeNode{
        TreeNode left=null;
        TreeNode right=null;
        int val;
       TreeNode(int val){
           this.val=val;
       }
    }
}
