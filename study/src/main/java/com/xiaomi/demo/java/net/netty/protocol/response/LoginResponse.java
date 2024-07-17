package com.xiaomi.demo.java.net.netty.protocol.response;

import com.xiaomi.demo.java.net.netty.protocol.Constants;
import com.xiaomi.demo.java.net.netty.protocol.commond.Command;
import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
@Data
public class LoginResponse extends BaseResponse {
    private Long userId;
    private String token;

    public static LoginResponse success(Long userId, String token) {
        LoginResponse response = new LoginResponse();
        response.setMagic(Constants.MAGIC_NUMBER);
        response.setVersion(Constants.VERSION_1);
        response.setCommand(Command.LOGIN_RESPONSE);
        response.setCode("200");
        response.setMessage("登录成功");
        response.setUserId(userId);
        response.setToken(token);
        return response;
    }
}
