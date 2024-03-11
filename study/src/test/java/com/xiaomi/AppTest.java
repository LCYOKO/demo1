package com.xiaomi;

import com.xiaomi.demo.DemoApplication;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, properties = "application-test.yaml")
public class AppTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Sql(value = {
            "/script/init.sql",
            "/script/data.sql"
    })
    @Test
    public void init() {
        System.out.println("init");
    }

    @Test
    public void test1() {
        System.out.println(jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<User>(User.class)));
    }

    @Test
    public void test2() {
        System.out.println(2);
    }

    @Data
    public static class User {
        private Long id;
        private String email;
        private int age;
    }
}
