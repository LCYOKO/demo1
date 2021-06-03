package com.xiaomi.demo;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.data.relational.core.sql.In;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author liuchiyun
 * @Date 2021/5/15 6:31 下午
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void test1(){
   subarraysDivByK(new int[]{2,-2,2,-4},6);
    }


    public int subarraysDivByK(int[] nums, int k) {
        int n=nums.length,ans=0;
        Map<Integer,Integer> map=new HashMap<>();
        map.put(0,1);
        int sum=0;
        for(int i=1;i<=n;i++){
            sum+=nums[i-1];
            int key=Math.abs(sum%k);
            if(map.containsKey(key)){
                ans+=map.get(key);
            }
            //    System.out.println(map);
            map.put(key,map.getOrDefault(key,0)+1);
        }
        return ans;
    }

    public int[] getBiggestThree(int[][] grid) {
        List<Node> list=new ArrayList<>();
        int n=grid.length,m=grid[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int sum=grid[i][j];
                if(i>=0 && i<n-2 && j>0 && j<m-1){
                    sum=grid[i][j]+grid[i+2][j]+grid[i+1][j-1]+grid[i+1][j+1];
                }

                list.add(new Node(i,j,sum));
            }
        }
        list.sort((o1,o2)->o1.val-o2.val);
        int index=0;
        List<Node> ans=new ArrayList<>();
        for(int i=list.size()-1;i>=0&&ans.size()<3;i--){
            if(index!=0 && check(ans.get(index-1),list.get(i))){
                continue;
            }
            ans.add(list.get(i));

        }
        index=0;
        int arr[]=new int[3];
        for(Node node:list){
            arr[index++]=node.val;
            if(index==3){
                break;
            }
        }

        return arr;
    }

    private boolean check(Node a,Node b){
        if((a.i==b.i && a.j==b.j+1) || (Math.abs(a.i-b.i)==1 && a.j==b.j)){
            return true;
        }
        return false;
    }
    class Node{
        int i;
        int j;
        int val;
        Node(int i,int j,int val){
            this.i=i;
            this.j=j;
            this.val=val;
        }
    }


    public String shiftingLetters(String s, int[] shifts) {
        int n=shifts.length;
        char str[]=s.toCharArray();
        for(int i=1;i<n;i++){
            shifts[i]+=shifts[i-1];
            shifts[i]%=26;
        }
        for(int i=0;i<str.length;i++){
            str[i]=(char)((str[i]-'a'+shifts[i])%26+'a');
        }
        return new String(str);
    }

    public String minWindow(String s, String t) {
        int table[]=new int[128];
        int n=s.length(),cnt=0,idx=0,len=Integer.MAX_VALUE,l=0,r=0;
        for(int i=0;i<t.length();i++){
            char c=t.charAt(i);
            if(table[c]==0){
                cnt++;
            }
            table[c]++;
        }
        while(r<n){
            char c=s.charAt(r);
            table[c]--;
            if(table[c]==0){
                cnt--;
            }
            while(cnt==0){
                if(r-l+1<len){
                    len=r-l+1;
                    idx=l;
                }
                table[s.charAt(l++)]++;
                while(l<=r&&table[s.charAt(l)]!=0){
                    table[s.charAt(l++)]++;
                }
                cnt++;
            }
            r++;
        }
        if(idx==Integer.MAX_VALUE){
            return "";
        }
        return s.substring(idx,idx+len);
    }




    public int minSpeedOnTime(int[] dist, double hour) {
        int r=1,l=1;
        for(int e:dist){
            r=Math.max(r,e);
        }
        int ans=-1;
        while(l<=r){
            int mid=(r-l)/2+l;
            double time=getTime(dist,mid);
            System.out.println(time);
            if(time<=hour){
                ans=mid;
                r=mid-1;
            }
            else l=mid+1;
        }
        return ans;

    }

    private double getTime(int[] dist,int s){
        double time=0;
        int n=dist.length;
        for(int i=0;i<n;i++){
            if(i==n-1){
                time+=((double)dist[i])/s;
            }
            else time+=dist[i]%s==0?dist[i]/s:dist[i]/s+1;
        }
        return time;
    }

    public boolean canReach(String s, int minJump, int maxJump) {
        int n=s.length();
        char c[]=s.toCharArray();
        if(c[n-1]!='0'){
            return false;
        }
        for(int i=0;i<n-1;){
            if(i==n-1){
                break;
            }
            int cur=i+maxJump>=n?n-1:i+maxJump;
            if(cur==n-1){
                if(c[cur]=='0'){
                    return true;
                }
                else return false;
            }
            int maxIndex=i;
            while(cur>=i+minJump){
                if(c[cur]=='0'){
                    maxIndex=cur;
                    break;
                }
                cur--;
            }
            if(maxIndex==i){
                return false;
            }
            i=maxIndex;
        }
        return true;
    }

    public boolean isNStraightHand(int[] hand, int W) {
        Map<Integer,Integer> map=new HashMap<>();
        int n=hand.length;
        if(n%W!=0){
            return false;
        }
        for(int e:hand){
            map.put(e,map.getOrDefault(e,0)+1);
        }
        Arrays.sort(hand);
        for(int i=0;i<n;i++){
            if(map.get(hand[i])==0){
                continue;
            }
            for(int j=0;j<W;j++){
                if(map.getOrDefault(hand[i]+j,0)==0){
                    return false;
                }
                map.put(hand[i]+j,map.get(hand[i]+j)-1);
            }
        }
        return true;
    }

}
