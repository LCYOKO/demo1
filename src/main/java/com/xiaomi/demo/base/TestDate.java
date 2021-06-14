package com.xiaomi.demo.base;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.logging.SimpleFormatter;

/**
 * @Author：liuchiyun
 * @Date: 2021/4/30
 */
public class TestDate {

    @Test
    public  void test(){
        StringBuilder builder = new StringBuilder();
        builder.deleteCharAt()
    }

    public boolean checkInclusion(String s1, String s2) {
        int table[]=new int[26];
        int n=s2.length(),l=0,r=0,cnt=0;
        for(int i=0;i<s1.length();i++){
            table[s1.charAt(i)-'a']++;
            cnt++;
        }
        while(r<n){
            char c=s2.charAt(r);
            if(table[c-'a']>0){
                cnt--;
            }
            table[c-'a']--;
            if(cnt==0){
                return true;
            }
            while(l<=r&&table[s2.charAt(r)-'a']==-1){
                if(l<r&&s2.charAt(l)!=c){
                    cnt++;
                }
                table[s2.charAt(l++)-'a']++;
            }
            r++;
        }
        return false;
    }
}
