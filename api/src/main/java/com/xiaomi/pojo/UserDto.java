package com.xiaomi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: liuchiyun
 * @Date: 2024/5/16
 */
@Data
public class UserDto implements Serializable {
    private long id;
    private String name;
    private Integer age;
}
