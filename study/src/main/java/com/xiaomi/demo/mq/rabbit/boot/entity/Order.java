package com.xiaomi.demo.mq.rabbit.boot.entity;

import lombok.Data;

@Data
public class Order {

    private String id;

    private String name;

    private String content;
}
