package com.xiaomi.demo.db.mysql;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author l
 * @Date 2021/1/23 23:20
 * @Version 1.0
 */
@Slf4j
public class DbPoolImpl implements DbPool {

    /**
     * 空闲连接池
     */
    private final LinkedBlockingQueue<Connection> idleConnectPool;

    /**
     * 活跃连接池
     */
    private final LinkedBlockingQueue<Connection> busyConnectPool;

    /**
     * 当前正在被使用的连接数
     */
    private final AtomicInteger activeSize = new AtomicInteger(0);

    /**
     * 最大连接数
     */
    private final int maxSize;

    public DbPoolImpl(int maxSize) {
        this.maxSize = maxSize;
        idleConnectPool = new LinkedBlockingQueue<>();
        busyConnectPool = new LinkedBlockingQueue<>();
    }

    /**
     * 获取一个连接
     */
    @Override
    public Connection getConnection() {
        // 从idle池中取出一个连接
        Connection connection = idleConnectPool.poll();
        if (connection != null) {
            // 如果有连接，则放入busy池中
            busyConnectPool.offer(connection);
            return connection;
        }
        // idle池中没有连接如果idle池中连接未满maxSize，就新建一个连接
        if (activeSize.get() < maxSize) {

            if (activeSize.incrementAndGet() <= maxSize) {
                connection = createConnection();
                busyConnectPool.offer(connection);
                return connection;
            }
        }

        try {
            connection = idleConnectPool.poll(10000, TimeUnit.MILLISECONDS);
            if (connection == null) {
                throw new RuntimeException("等待连接超时");
            }
        } catch (Exception e) {
            log.error("get connection failed", e);
        }
        return connection;
    }

    /**
     * 释放一个连接
     */
    @Override
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            busyConnectPool.remove(connection);
            idleConnectPool.offer(connection);
        }
    }

    /**
     * 销毁连接池
     */
    @Override
    public void destroy() {

    }

    /**
     * 定时对连接进行健康检查
     * 注意：只能对idle连接池中的连接进行健康检查，
     */
    public void check() {
        for (int i = 0; i < activeSize.get(); i++) {
            Connection connection = idleConnectPool.poll();
            try {
                boolean valid = connection.isValid(2000);
                if (!valid) {
                    // 如果连接不可用，则创建一个新的连接
                    connection = createConnection();
                }
                idleConnectPool.offer(connection);
            } catch (Exception ex) {
                log.info("check connection failed", ex);
            }
        }
    }

    private Connection createConnection() {
        return null;
    }
}
