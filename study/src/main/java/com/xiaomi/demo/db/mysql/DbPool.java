package com.xiaomi.demo.db.mysql;

import java.sql.Connection;

/**
 * @Author l
 * @Date 2021/1/23 23:19
 */
public interface DbPool {
    /**
     * 获取一个连接
     */
    Connection getConnection();

    /**
     * 释放一个连接
     */
    void releaseConnection(Connection connection);

    /**
     * 销毁连接池
     */
    void destroy();
}
