package com.xiaomi.demo.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @Author：liuchiyun
 * @Date: 2021/6/2
 */
public class ZkTest {

    private CuratorFramework client;
    @Before
    public void init(){
        RetryPolicy retry = new ExponentialBackoffRetry(2000,3);
        client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(100000)
                .retryPolicy(retry)
                .connectionTimeoutMs(1000)
                .build();
        client.start();
    }
    @After
    public void close(){
        client.close();
    }

    @Test
    public void test() throws Exception {
//     testCreate();
//     testSet();
//        testDel();
     testGet();
    }

    private void testCreate() throws Exception {
        client.create()
                .creatingParentsIfNeeded()
              .withMode(CreateMode.PERSISTENT)
              .forPath("/test_temp1/a","123".getBytes());
    }

    private void testGet() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = client.getData().
                storingStatIn(stat)
                .forPath("/test_temp1");
        System.err.println(new String(bytes));
        System.err.println(stat);
        List<String> list = client.getChildren().forPath("/test_temp1");
        System.err.println(list);
    }

    private void testSet() throws Exception {
        client.setData()
                .forPath("/test_temp1","456".getBytes());
    }

    private void testDel() throws Exception {
        client.delete().deletingChildrenIfNeeded()
                .forPath("/test_temp1");
    }
}
