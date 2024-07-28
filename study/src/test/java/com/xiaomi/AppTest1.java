package com.xiaomi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author liuchiyun
 * @Date 2024/7/28
 */

public class AppTest1 extends TestBase {
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test1() {
        System.out.println(jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<AppTest.User>(AppTest.User.class)));
    }
}
