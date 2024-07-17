package com.xiaomi.demo.java.net.netty.protocol;


import com.xiaomi.common.utils.JsonUtils;
import com.xiaomi.demo.java.net.netty.protocol.commond.Command;
import com.xiaomi.demo.java.net.netty.protocol.request.LoginRequest;
import com.xiaomi.demo.java.net.netty.protocol.response.LoginResponse;
import io.netty.buffer.ByteBuf;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/17
 */
public abstract class PacketUtils {
    public static ByteBuf encode(BasePacket packet, ByteBuf byteBuf) {
        byteBuf.writeInt(packet.getMagic());
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(packet.getCommand());
        byte[] bytes = JsonUtils.toByteArray(packet);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }


    public static BasePacket decode(ByteBuf byteBuf) {
        int magicNumber = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        if (command == Command.LOGIN_REQUEST) {
            return JsonUtils.as(bytes, LoginRequest.class);
        } else {
            return JsonUtils.as(bytes, LoginResponse.class);
        }
    }

}
