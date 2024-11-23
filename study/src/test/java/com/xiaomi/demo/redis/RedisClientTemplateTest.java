package com.xiaomi.demo.redis;

import com.xiaomi.demo.DemoApplication;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/17
 */
@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Slf4j
@Ignore
public class RedisClientTemplateTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> objectRedisTemplate;

    @Test
    public void test1() {
        redisTemplate.opsForValue().set("user:1", User.of(1L, "lisi"));
        stringRedisTemplate.opsForValue().set("user:2", User.of(1L, "lisi").toString());

        Object o = redisTemplate.opsForValue().get("user:1");
        String s = stringRedisTemplate.opsForValue().get("user:2");
        log.info("user1: {}, user2:{}", o, s);
    }

    @Test
    public void test2() {
        objectRedisTemplate.opsForValue().set("user:1", User.of(1L, "lisi"));
        Object o = objectRedisTemplate.opsForValue().get("user:1");
        log.info("user:{}", o);
    }


    @Data
    static class User implements Serializable {
        private Long id;
        private String name;

        public static User of(Long id, String name) {
            User user = new User();
            user.setId(id);
            user.setName(name);
            return user;
        }
    }
}
