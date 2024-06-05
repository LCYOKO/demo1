package com.xiaomi.demo.design;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
public class UserServiceImpl implements UserService {
    @Override
    public User selectOne() {
        User user = new User();
        user.setId(1L);
        user.setName("lisi");
        return user;
    }
}
