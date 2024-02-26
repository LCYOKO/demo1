package com.xiaomi.demo.rpc.thrift;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 8:45 下午
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {
        Server server = new Server(Server.ServerModel.NOT_BLOCK);
        Client client = new Client();
        Thread.sleep(10000L);
        client.getBookInfo(1);
    }


}
