package com.xiaomi.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
      int arr[]=new int[]{-1,1,2,-2,2};
      int min=Integer.MAX_VALUE;
      int cur=0;
      for(int i=0;i<arr.length;i++){
          if(cur<0 &&arr[i]<0){
              cur=arr[i];
          }
          else if(cur>0 && arr[i]>0){
              cur=arr[i];
          }
          else cur+=arr[i];
          min=Math.min(Math.abs(cur),min);
      }
      System.out.println(min);
    }

}
