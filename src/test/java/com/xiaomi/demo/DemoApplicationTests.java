package com.xiaomi.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    void contextLoads() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("123","123");
    }

    @Test
    void test(){
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();

        //opsForZSet.add("fan5", "a", 1);
        //opsForZSet.add("fan5", "b", 3);
        //opsForZSet.add("fan5", "c", 2);
        //opsForZSet.add("fan5", "d", -1);
        //当从高到低时，确定排序集中的值的元素的索引。
        //System.out.println(opsForZSet.reverseRange("fan5", 0,-1));
         while (true){
             Set<ZSetOperations.TypedTuple<String>> delay = opsForZSet.reverseRangeWithScores("delay1", 0, 0);
              AtomicBoolean flag= new AtomicBoolean(false);
             if(!delay.isEmpty()){
                 delay.forEach(e->{
                     Double score = e.getScore();
                     long l=score.longValue();
                     long now=new Date().toInstant().getEpochSecond();
                     System.out.println(now);
                     if(l<now){
                         System.out.println();
                     }
                     else {
                         try {
                             Thread.sleep((l-now)*1000);
                         } catch (InterruptedException ex) {
                             ex.printStackTrace();
                         }
                     }
                     System.out.println(new Date().toInstant().getEpochSecond());

                     flag.set(true);
                 });
             }
             if(flag.get()){
                 break;
             }

         }
            //Set<ZSetOperations.TypedTuple<String>> delay = opsForZSet.reverseRangeWithScores("fan5", 0, 0);

            //System.out.println(delay);

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
