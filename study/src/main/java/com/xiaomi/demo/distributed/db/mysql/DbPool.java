package com.xiaomi.demo.distributed.db.mysql;

import java.sql.Connection;

/**
 * @Author l
 * @Date 2021/1/23 23:19
 */
public interface DbPool {

    /**
     * 连接池初始化
     */
    void init();

    /**
     * 获取一个连接
     *
     * @return
     */
    Connection getConnection();

    /**
     * 释放一个连接
     *
     * @param connection
     */
    void releaseConnection(Connection connection);

    /**
     * 销毁连接池
     */
    void destroy();
}
