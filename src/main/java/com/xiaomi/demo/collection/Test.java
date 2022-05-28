package com.xiaomi.demo.collection;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author l
 * @Date 2021/1/4 10:13
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
    }

    @org.junit.Test
    public void function() throws InterruptedException {

        BlockingQueue<Integer>  bq= new ArrayBlockingQueue<>(1);
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        bq.put(1);
        new Thread(()-> {
            try {
                Thread.sleep(1000);
                bq.take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        bq.put(2);

    }

    @org.junit.Test
    public void sortTest(){
        int arr[]={3,4,1,2,6,6};
        heapfySort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public void quickSort(int nums[]){
        sort(nums,0,nums.length-1);
    }

    private void sort(int nums[],int l,int r){
        if(l>=r){
            return ;
        }
        int i=l;
        int j=r;
        while(i<j){
            while(i<j&&nums[j]>=nums[l]) j--;
            while(i<j&&nums[i]<=nums[l]) i++;
            if(i<j){
                int temp=nums[i];
                nums[i]=nums[j];
                nums[j]=temp;
            }
        }
        int temp=nums[l];
        nums[l]=nums[i];
        nums[i]=temp;
        sort(nums,l,i-1);
        sort(nums,i+1,r);
    }
    static int temp[]=new int[100];
    public void mergeSort(int nums[]){
        sort1(nums,0,nums.length-1);
    }

    private void sort1(int nums[],int l ,int r){
        if(l>=r) return ;
        int mid=(r-l)/2+l;
        sort1(nums,l,mid);
        sort1(nums,mid+1,r);
        merge(nums,l,mid,1);
    }

    private void merge(int nums[],int l,int mid ,int r){
        int i=l;
        int j=mid+1;
        int idx=0;
        while(i<=mid && j<=r){
            if(nums[i]<=nums[j]){
                temp[idx++]=nums[i++];
            }
            else{
                temp[idx++]=nums[j++];
            }
        }
        while(i<=mid) temp[idx++]=nums[i++];
        while (j<=r) temp[idx++]=nums[j++];
        idx=0;
        while(idx+l<=r) nums[l+idx]=temp[idx++];
    }

    public void heapfySort(int nums[]){
        int n=nums.length;
         for(int i=n/2-1;i>=0;i--){
                adjustHeap(nums,i,n);
         }
         for(int i=n-1;i>0;i--){
             swap(nums,0,i);
             adjustHeap(nums,0,i);
         }
    }



    private void adjustHeap(int nums[],int i, int n){
             int left=i*2+1;
             int right=i*2+2;
             int maxIdx=i;
             if(left<n&&nums[left]>nums[maxIdx]){
                 maxIdx=left;
             }
             if(right<n&&nums[right]>nums[maxIdx]){
                 maxIdx=right;
             }
             if(i!=maxIdx){
                 swap(nums,i,maxIdx);
                 adjustHeap(nums,maxIdx,n);
             }
    }

    private void swap(int nums[], int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }

}
