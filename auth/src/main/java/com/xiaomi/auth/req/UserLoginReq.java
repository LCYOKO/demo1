package com.xiaomi.auth.req;

import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/18
 */
@Data
public class UserLoginReq {
    private String username;
    private String password;
}
