package com.xiaomi.service;

import com.xiaomi.pojo.UserDto;

/**
 * @Author: liuchiyun
 * @Date: 2024/5/16
 */
public interface UserService {
    UserDto getById(long id);
}
