package com.xiaomi.demo.java.net.netty.protocol;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 * <p>
 * 4字节魔术+1字节版本号+1字节command+4字节数据包长度+数据包
 */
@Data
public class BasePacket {
    @JsonIgnore
    private int magic;
    @JsonIgnore
    private byte version;
    @JsonIgnore
    private byte command;
}
