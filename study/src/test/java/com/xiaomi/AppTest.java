package com.xiaomi;

import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = {
        "/script/user_test.sql"
})
public class AppTest extends TestBase {
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test1() {
        System.out.println(jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<User>(User.class)));
    }


    @Data
    public static class User {
        private Long id;
        private String email;
        private int age;
    }
}
