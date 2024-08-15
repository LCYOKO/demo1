package com.xiaomi.demo.rule.liteflow;

import com.xiaomi.demo.DemoApplication;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/14
 * https://liteflow.cc/pages/v2.10.X/33833a/
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class LiteFlowTest {

    @Autowired
    private FlowExecutor flowExecutor;

    @Test
    public void test1() {
        OrderContext context = OrderContext.of(1L, "用户订单1");
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg", context);
        log.info("response:{}", response);
    }
}
