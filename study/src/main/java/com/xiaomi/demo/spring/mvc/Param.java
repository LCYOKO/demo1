package com.xiaomi.demo.spring.mvc;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
@Data
public class Param {
    @NotEmpty
    private String msg;
}
