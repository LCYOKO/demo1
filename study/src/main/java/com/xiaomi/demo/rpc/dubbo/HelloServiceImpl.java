package com.xiaomi.demo.rpc.dubbo;

import com.xiaomi.pojo.UserDto;
import com.xiaomi.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class HelloServiceImpl implements UserService {
    @Override
    public UserDto getById(long id) {
        UserDto userDto = new UserDto();
        userDto.setName("lisi");
        return userDto;
    }
}
