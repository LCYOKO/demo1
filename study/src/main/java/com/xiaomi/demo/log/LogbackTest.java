package com.xiaomi.demo.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author liuchiyun
 * @Date 2024/6/17
 */
@Slf4j
public class LogbackTest {
    /**
     * <logger>通过name和level来控制是否打印log,
     * <root>是全局logger也默认是所有的logger的父类
     *
     * <logger name="com.qbhj.logback.controller" level="INFO" additivity="true">
     * <!-- 控制台 -->
     * <appender-ref ref="CONSOLE"/>
     * </logger>
     *
     * <root level="INFO">
     * <!-- 控制台 -->
     * <appender-ref ref="CONSOLE"/>
     * </root>
     * 上述例子表述，com.qbhj.logback.controller类下的日志会打印2次
     */
    @Test
    public void testInfo() {
        log.info("test 123");
    }

}
