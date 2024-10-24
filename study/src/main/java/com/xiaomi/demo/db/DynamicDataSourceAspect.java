package com.xiaomi.demo.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


/**
 * @Author: liuchiyun
 * @Date: 2024/7/15
 */
//@Aspect
public class DynamicDataSourceAspect {

    @Pointcut("execution(public * com.xiaomi.demo.controller..*(..))")
    public void pointCutAround() {
    }

    /**
     * 根据数据上下文切换数据源
     */
    @Before(value = "pointCutAround()")
    public void around(JoinPoint point) {
//        AppContext context = AppContext.getContext();
//        String flag = context.getFlag();
//        if (StringUtils.isNotEmpty(flag) && flag.equals(DataSourceNames.TEST)) {
//            MDC.put("datasource", "shadow");
//            DynamicDataSource.setDataSource(DataSourceNames.SHADOW);
//        } else {
//            MDC.put("datasource", "produce");
//            DynamicDataSource.setDataSource(DataSourceNames.MASTER);
//        }
    }
}
