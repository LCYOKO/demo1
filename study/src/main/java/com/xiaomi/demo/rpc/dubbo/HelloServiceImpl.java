package com.xiaomi.demo.rpc.dubbo;

import com.xiaomi.pojo.UserDto;
import com.xiaomi.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

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

    @Override
    public CompletableFuture<UserDto> getByIdAsync(long id) {
        return CompletableFuture.supplyAsync(() -> {
            UserDto userDto = new UserDto();
            userDto.setName("lisi");
            userDto.setAge(port);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return userDto;
        });
    }
}
