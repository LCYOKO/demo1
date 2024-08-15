package com.xiaomi.demo.rule.liteflow;

import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/15
 */
@Data
public class OrderContext {
    private Long orderNo;
    private String name;

    public static OrderContext of(Long orderNo, String name) {
        OrderContext context = new OrderContext();
        context.setOrderNo(orderNo);
        context.setName(name);
        return context;
    }
}
