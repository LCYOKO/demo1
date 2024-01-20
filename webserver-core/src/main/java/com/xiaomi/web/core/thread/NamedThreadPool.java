package com.xiaomi.web.core.thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
public class NamedThreadPool implements ThreadFactory {
    private final AtomicInteger count;
    private final String prefix;

    public NamedThreadPool(String prefix) {
        this.count = new AtomicInteger(0);
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, prefix + count.getAndIncrement());
    }
}
