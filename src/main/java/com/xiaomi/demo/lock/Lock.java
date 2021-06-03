package com.xiaomi.demo.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Author：liuchiyun
 * @Date: 2021/6/2
 */
public interface Lock {

    boolean tryLock();

    boolean lock();

    boolean lock(Long timeout, TimeUnit timeUnit);

    void unlock();
}
