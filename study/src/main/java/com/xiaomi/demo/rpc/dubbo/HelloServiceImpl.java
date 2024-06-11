package com.xiaomi.demo.rpc.dubbo;

import com.xiaomi.pojo.UserDto;
import com.xiaomi.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

@DubboService
public class HelloServiceImpl implements UserService {
    @Value("${server.port}")
    private int port;

    @Override
    public UserDto getById(long id) {
        UserDto userDto = new UserDto();
        userDto.setName("lisi");
        userDto.setAge(port);
        return userDto;
    }
}
