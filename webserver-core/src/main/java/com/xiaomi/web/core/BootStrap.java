package com.xiaomi.web.core;


import com.xiaomi.web.core.network.endpoint.Endpoint;
import com.xiaomi.web.core.network.endpoint.EndpointType;
import com.xiaomi.web.core.util.PropertyUtil;

import java.util.Scanner;

/**
 * @author sinjinsong
 * @date 2018/3/6
 */
public class BootStrap {

    /**
     * 服务器启动入口
     * 用户程序与服务器的接口
     */
    public static void run() {
        String port = PropertyUtil.getProperty("server.port");
        if(port == null) {
            throw new IllegalArgumentException("server.port 不存在");
        }
        EndpointType type = EndpointType.getOrThrow(PropertyUtil.getProperty("server.connector"));
        Endpoint server = Endpoint.getInstance(type);
        server.start(Integer.parseInt(port));
        Scanner scanner = new Scanner(System.in);
        String order;
        while (scanner.hasNext()) {
            order = scanner.next();
            if (order.equals("EXIT")) {
                server.close();
                System.exit(0);
            }
        }
    }
}
