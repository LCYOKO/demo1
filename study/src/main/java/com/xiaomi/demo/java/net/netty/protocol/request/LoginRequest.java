package com.xiaomi.demo.java.net.netty.protocol.request;

import com.xiaomi.demo.java.net.netty.protocol.BasePacket;
import com.xiaomi.demo.java.net.netty.protocol.Constants;
import com.xiaomi.demo.java.net.netty.protocol.commond.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends BasePacket {
    private String userName;
    private String password;

    public static LoginRequest of(String userName, String password) {
        LoginRequest request = new LoginRequest(userName, password);
        request.setMagic(Constants.MAGIC_NUMBER);
        request.setVersion(Constants.VERSION_1);
        request.setCommand(Command.LOGIN_REQUEST);
        return request;
    }
}
