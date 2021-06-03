package com.xiaomi.demo.net;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/10
 */
public class TestCase1 {

    @Test
    public void test1(){
      lexicalOrder(13);
    }

    List<Integer> ans=new ArrayList<>();
    public List<Integer> lexicalOrder(int n) {
        dfs(n,1);
        return  ans;
    }

    private void dfs(int n,int cur){
        if(cur>n){
            return;
        }

        for(int i=0;i<=9;i++){
            if(cur+i>n || (cur==1 && i==9)){
                return;
            }
            ans.add(cur+i);
            dfs(n,(cur+i)*10);
        }
    }

    private void swap(int i,int j,int nums[]){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }

    public int maximalSquare(char[][] matrix) {
        int max=0,n=matrix.length,m=matrix[0].length;
        for(int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                if(i !=0 && j!=0 && matrix[i][j]!='0'){
                    matrix[i][j]=(char)(Math.min(Math.min(matrix[i-1][j],matrix[i][j-1]),matrix[i-1][j-1])+1);
                }
                max=Math.max(max,matrix[i][j]-'0');
            }
        }
        return max;
    }


    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> maxHeap=new PriorityQueue<>((o1,o2)->{
            return getSum(o1)-getSum(o2);
        });
        List<Integer> l=null;
        int n=nums1.length,m=nums2.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                l=new ArrayList<>();
                l.add(nums1[i]);
                l.add(nums2[j]);
                if(maxHeap.size()==k){
                    if(getSum(maxHeap.peek())>getSum(l)) {
                        maxHeap.poll();
                        maxHeap.add(l);
                    }
                }
                else maxHeap.add(l);

            }
        }
        return maxHeap.stream().collect(Collectors.toList());
    }

    private int getSum(List<Integer> l){
        return l.get(0)+l.get(1);
    }
    public String validIPAddress(String IP) {
        String IP4[]=IP.split("\\.");
        String IP6[]=IP.split(":");
        if(IP4.length>1 && IP6.length>1){
            return "Neither";
        }
        if(IP.charAt(IP.length()-1)=='.'){
            return "Neither";
        }
        if(IP.charAt(IP.length()-1)==':'){
            return "Neither";
        }
        if(checkIP4(IP4)){

            return "IPv4";
        }

        if(checkIP6(IP6)){
            return "IPv6";
        }
        return "Neither";
    }

    private boolean checkIP4(String[] str){
        if(str.length!=4){
            return false;
        }
        for(int i=0;i<4;i++){
            int num=0;

            for(int j=0;j<str[i].length();j++){
                char c=str[i].charAt(j);
                if(c<'0' || c>'9'){
                    return false;
                }
                if(j!=0 && c=='0'){
                    return false;
                }
                num=num*10+c-'0';
            }
            if(num>255){
                return false;
            }
        }
        return true;
    }
    private boolean checkIP6(String[] str){
        if(str.length!=8){
            return false;
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<str[i].length();j++){
                if(str[i].length()>4){
                    return false;
                }
                char c=str[i].charAt(j);
                if(!((c>=0 || c<=9)||(c>='a' || c<='f'))){
                    return false;
                }

            }
        }
        return  true;
    }

    @Test
    public void test(){
//        0 0 1 0 0
//        0 0 0 0 0
//        0 0 0 1 0
//        1 1 0 1 1
//        0 0 0 0 0
        int[][] matrix=new int[][]{
                {0,0,1,0,0},
                {0,0,0,0,0},
                {0,0,0,1,0},
                {1,1,0,1,1},
                {0,0,0,0,0}
        };
//        dfs(0,4,matrix,new int[]{3,2});
//          hasPath(matrix,new int[]{0,4},new int[]{3,2});
    }





    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans=new ArrayList<>();
        int n=matrix.length;
        if(n==0){
            return  ans;
        }
        int m=matrix[0].length;
        if(m==0){
            return ans;
        }
        int u=0,d=n-1,l=0,r=m-1;
        while(true){
            for(int i=l;i<=r;i++){
                ans.add(matrix[u][i]);
            }
            if(++u>d){
                break;
            }
            for(int i=u;i<=d;i++){
                ans.add(matrix[i][r]);
            }
            if(--r<l){
                break;
            }
            for(int i=r;i>=l;i--){
                ans.add(matrix[d][i]);
            }
            if(--d<u){
                break;
            }
            for(int i=d;i>=u;i--){
                ans.add(matrix[i][l]);
            }
            if(++l>r){
                break;
            }
        }
        return  ans;
    }


     @Test
    public void testSkpiList(){
        Skiplist skiplist = new Skiplist();
        skiplist.add(0);
        skiplist.add(5);
        skiplist.add(2);
        skiplist.add(1);
        skiplist.search(0);
//        skiplist.print();
        skiplist.erase(5);
        skiplist.search(2);
        skiplist.search(3);
        skiplist.search(2);

    }

    class Skiplist {
        Node head;
        int maxLevel;
        int curLevel;
        double limit=0.5D;
        Random random;
        public Skiplist() {
            maxLevel=10;
            curLevel=1;
            random=new Random();
            head=new Node(-1);

        }

        public boolean search(int target) {
            Node node=search(head,target);
            if(node==null){
                return false;
            }
            return  true;
        }

        public void add(int num) {
            int l=getLevel();
            while(l!=curLevel){
                if(l>curLevel){
                Node node=new Node(-1);
                node.down=head;
                head=node;
                curLevel++;
                }
                else {

                }
            }
            Node node=head;
            Node parent=null;
            while(node!=null){
                Node temp=node;
                while(temp.next!=null && temp.next.val<num){
                    temp=temp.next;
                }
                Node newNode=new Node(num);
                temp.next=newNode;
                if(parent!=null){
                    parent.down=newNode;
                }
                parent=newNode;
                node=node.down;
            }
        }

        public boolean erase(int num) {
            Node node= search(head,num);
            if(node==null ){
                return  false;
            }
            while(node!=null){
                Node temp=node;
                while(temp.next!=null &&temp.next.val<num){
                    temp=temp.next;
                }
                temp.next=temp.next.next;
                node=node.down;
            }
            return  true;
        }

        private int getLevel(){
            int cur=1;
            while(cur<maxLevel && random.nextDouble()<limit){
                cur++;
            }
            return  cur;
        }

        private Node search(Node node, int target){
            if(node==null){
                return  null;
            }
            while(node.next!=null && node.next.val<target){
                node=node.next;
            }
            if(node.next!=null && target==node.next.val){
                return node;
            }
            return search(node.down,target);
        }

        private void print(){
            Node node=head;
            while(node!=null){
                Node temp=node;
                while(temp.next!=null){
                    System.out.print(temp.next.val+" ");
                    temp=temp.next;
                }
                System.out.println();
                node=node.down;
            }
        }
        class Node{
            Node next;
            Node down;
            int val;
            Node(int val){
                this.val=val;
            }
        }
    }


    class LRUCache {

        LinkedList<Node> l;
        Map<Integer,Node> map;
        int cap;
        public LRUCache(int capacity) {
            cap=capacity;
            map=new HashMap<>();
            l=new LinkedList<>();
        }

        public LRUCache() {

        }

        public int get(int key) {
            if(!map.containsKey(key)){
                return -1;
            }
            Node node=map.get(key);
            l.remove(node);
            l.addLast(node);
            return node.val;
        }

        public void put(int key, int val) {
            Node node =new Node(key,val);
            if(map.containsKey(key)){
                Node node1=map.get(key);
                l.remove(node1);
                map.put(key,node);
                l.addLast(node);
                return;
            }
             if( l.size()==cap){
                Node node1=l.getFirst();
                map.remove(node1.key);
                l.removeFirst();

            }

                map.put(key, node);
                l.addLast(node);

        }
        class Node{
            int key;
            int val;
            Node(int key, int val){
                this.key=key;
                this.val=val;
            }
        }
    }
}
