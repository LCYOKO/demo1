package com.xiaomi.demo.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author liuchiyun
 * @Date 2024/6/17
 */
@Slf4j
public class CuratorTest {

    private CuratorFramework client;

    private final String path = "/test";

    @Before
    public void init() {
        RetryPolicy retry = new ExponentialBackoffRetry(2000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(100000)
                .retryPolicy(retry)
                .connectionTimeoutMs(1000)
                .build();
        client.start();
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    public void testCreate() throws Exception {
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/test/a", "123".getBytes());
    }

    @Test
    public void testGet() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = client.getData().
                storingStatIn(stat)
                .forPath("/test/a");
        log.info("stat:{}, bytes:{}", stat, new String(bytes));
        List<String> list = client.getChildren().forPath("/test");
        log.info("lists:{}", list);
    }

    @Test
    public void testUpdate() throws Exception {
        client.setData().forPath(path + "/a", "{\"name\":\"lisi\"}".getBytes());
        byte[] bytes = client.getData().forPath("/test/a");
        log.info("data:{}", new String(bytes));
    }

    @Test
    public void testDel() throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath("/test");
        List<String> list = client.getChildren().forPath("/test");
        log.info("lists:{}", list);
    }


    @SneakyThrows
    @Test
    public void testLock() throws Exception {
        InterProcessMutex mutex = new InterProcessMutex(client, "/demo");
        mutex.acquire();
        log.info("get lock success");
        Thread.sleep(20000);
        mutex.release();
    }

    /**
     * LeaderElection 获取leader选举权后会定期释放
     */
    @Test
    public void testLeaderElection() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        LeaderSelector leaderSelector = new LeaderSelector(client, "/leader", new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                log.info("takeLeadership success.");
                countDownLatch.countDown();
            }
        });
        leaderSelector.setId("123");
        leaderSelector.start();
        log.info("isLeader:{}", leaderSelector.hasLeadership());
        countDownLatch.await();
        log.info("isLeader:{}", leaderSelector.hasLeadership());
    }

    @Test
    public void testLeaderLatch() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        LeaderLatch leaderSelector = new LeaderLatch(client, "/leader", "123");
        leaderSelector.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                log.info("is leader");
                countDownLatch.countDown();
            }

            @Override
            public void notLeader() {
                log.info("not leader");
            }
        });
        leaderSelector.start();
        log.info("isLeader:{}", leaderSelector.hasLeadership());
        countDownLatch.await();
        log.info("isLeader:{}", leaderSelector.hasLeadership());
    }

    @Test
    public void testQueue() {

    }
}
