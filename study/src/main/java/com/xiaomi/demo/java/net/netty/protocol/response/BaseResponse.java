package com.xiaomi.demo.java.net.netty.protocol.response;

import com.xiaomi.demo.java.net.netty.protocol.BasePacket;
import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
@Data
public class BaseResponse extends BasePacket {
    private String code;
    private String message;
}
