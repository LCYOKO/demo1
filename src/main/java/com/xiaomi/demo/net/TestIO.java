package com.xiaomi.demo.net;

import io.netty.channel.epoll.EpollSocketChannel;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.data.relational.core.sql.In;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class TestIO {

    @Test
    public void test() throws FileNotFoundException {
//
//        Random random = new Random();
//        for(int i=0;i<20;i++){
//            System.out.println(random.nextDouble());
//        }
//
          PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();
          countSegments("Of all the gin joints in all the towns in all the world,   ");
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

    public int findUnsortedSubarray(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return 0;
        }
        int n = nums.length;
        Deque<Integer> que = new ArrayDeque<>();
        int l = -1, r = -1;
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            while (!que.isEmpty() && ( i - que.peekLast() > 1 || nums[i]<nums[que.peekLast()])) {
                int idx = que.pollLast();
                if (l == -1 && flag) {
                    l = idx;
                } else if (flag) {
                    r = idx;
                }
                flag =false;
            }
            que.addLast(i);
        }
        if (l == r && l == -1) {
            return 0;
        }
        return r-l+1;
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


    public int findMaxConsecutiveOnes(int[] nums) {
        int max=0,cur=0,n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i]==1){
                cur++;
            }
            else cur=0;
            max=Math.max(max,cur);
        }
        return cur;
    }

    public String reverseStr(String s, int k) {
        int n=s.length(),l=0,r=0;
        if(k==1 || k>=n){
            return  s;
        }
        char[]c=s.toCharArray();
        while(r<n){
            if(r-l+1==k){
                swap(c,l,r);
                l=r+1;
            }
            r++;
        }
        if(l<n){
            swap(c,l,r-1);
        }
        return new String(c);
    }

    private void swap(char[]c,int i,int j){
        while(i<j){
            char temp=c[i];
            c[i++]=c[j];
            c[j--]=temp;
        }
    }

    public int countSegments(String s) {
        int cnt=0,i=0;
        if(s.length()==0){
            return cnt;
        }
        for(;i<s.length();i++){
            if(i!=0 && !isAlpha(s.charAt(i)) && isAlpha(s.charAt(i-1))){
                System.out.println(s.charAt(i));
                cnt++;
            }
        }
        if(isAlpha(s.charAt(i-1))){
            cnt++;
        }
        return cnt;
    }

    private boolean isAlpha(char c){
        if(c==' '){
            return false;
        }
        return true;
    }
}
