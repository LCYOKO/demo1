package com.xiaomi.demo.net;

import io.netty.channel.epoll.EpollSocketChannel;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.web.bind.WebDataBinder;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class TestIO {

    @Test
    public void test() throws FileNotFoundException {
        MyCircularQueue queue = new MyCircularQueue(8);
        queue.enQueue(3);
        queue.enQueue(9);
        queue.enQueue(5);
        queue.enQueue(0);
        queue.deQueue();
        queue.deQueue();
        System.out.println(queue.Rear());
    }

    class MyCircularQueue {
        int head,tail,size;
        int data[];
        public MyCircularQueue(int k) {
            head=tail=0;
            size=k+1;
            data=new int[size];
        }

        public boolean enQueue(int value) {
//        1 ,2 , 3 , 4
            if(isFull()){
                return false;
            }
            data[tail]=value;
            tail=(tail+1)%size;
            return true;
        }

        public boolean deQueue() {
            if(isEmpty()){
                return false;
            }
            tail=(tail+size-1)%size;
            return true;
        }

        public int Front() {
            if (isEmpty()){
                return -1;
            }
            return data[head];
        }

        public int Rear() {
            if(isEmpty()){
                return -1;
            }
            return data[(tail+size-1)%size];
        }

        public boolean isEmpty() {
            return head==tail;

        }

        public boolean isFull() {
            return head==(tail+1)%size;
        }
    }


    public int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int n=nums.length,ans=0;
//   1 1 3 4 5
        for(int i=0;i<n-1;i++){
            int l=i,r=n-1;
            if(l!=0 && nums[l]==nums[l-1]){
                continue;
            }
            l=l+1;
            while(l<r){
                int mid=(r-l)/2+l;
                int diff=Math.abs(nums[mid]-nums[i]);
                if(diff==k){
                    ans++;
                    break;
                }
                if(diff<k){
                    l=mid+1;
                }
                else r=mid-1;
            }
        }
        return  ans;
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

    class MyLinkedList {
        Node head;
        Node tail;
        int size;
        /** Initialize your data structure here. */
        public MyLinkedList() {
            head=new Node(-1);
            tail=head;
            size=0;
        }

        /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
        public int get(int index) {
            if(index>=size){
                return  -1;
            }
            Node temp=head.next;
            while(index>0){
                temp=temp.next;
                index--;
            }
            return temp.val;
        }

        /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
        public void addAtHead(int val) {
            Node node=new Node(val);
            node.next=head.next;
            head.next=node;
            if(tail==head){
                tail=node;
            }
            size++;
        }

        /** Append a node of value val to the last element of the linked list. */
        public void addAtTail(int val) {
            if(head==tail){
                addAtHead(val);
            }
            else{
                Node node=new Node(val);
                tail.next=node;
                tail=node;
            }
            size++;
        }

        /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
        public void addAtIndex(int index, int val) {
            if(index==0){
                addAtHead(val);
            }
            else if(index>=size){
                addAtTail(val);
            }
            else {
                Node temp=head;
                while(index>0){
                    temp=temp.next;
                    index--;
                }
                Node node=new Node(val);
                node.next=temp.next;
                temp.next=node;
                size++;
            }

        }

        /** Delete the index-th node in the linked list, if the index is valid. */
        public void deleteAtIndex(int index) {
            if(index>=size){
                return;
            }
            Node temp=head;
            while(index>0){
                temp=temp.next;
                index--;
            }

            temp.next=temp.next.next;
            if(index==size-1){
                tail=temp.next;
            }
            size--;
        }

        class Node{
            int val;
            Node next;
            Node(int val){
                this.val=val;
            }
        }
    }
}
