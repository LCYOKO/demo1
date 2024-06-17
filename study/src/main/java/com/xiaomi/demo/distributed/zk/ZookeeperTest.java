package com.xiaomi.demo.distributed.zk;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @Author liuchiyun
 * @Date 2024/6/17
 */
public class ZookeeperTest {
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 1000, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void testCreate() {


    }

    public void testSetData() {
        
    }
}
