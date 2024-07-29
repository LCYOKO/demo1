package com.xiaomi;

import com.xiaomi.demo.DemoApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author liuchiyun
 * @Date 2024/7/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles(profiles = "test")
public class TestBase {
}
