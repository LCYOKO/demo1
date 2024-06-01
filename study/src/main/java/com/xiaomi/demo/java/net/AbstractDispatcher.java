package com.xiaomi.demo.java.net;

import cn.hutool.core.thread.NamedThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
public abstract class AbstractDispatcher<T> {
    protected ThreadPoolExecutor threadPoolExecutor;

    public AbstractDispatcher() {
        ThreadFactory threadFactory = new NamedThreadFactory("Worker Pool-", false);
        this.threadPoolExecutor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 关闭
     */
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    public abstract void doDispatch(T socketWrapper);
}
