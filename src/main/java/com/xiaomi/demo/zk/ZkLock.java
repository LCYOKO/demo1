package com.xiaomi.demo.zk;

import com.xiaomi.demo.lock.Lock;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author：liuchiyun
 * @Date: 2021/6/2
 */
@AllArgsConstructor
public class ZkLock implements Lock {

    private CuratorFramework client;

    private String lockPath;

    private String lockName;

    private ConcurrentMap<Thread,LockData> lockMetaData=new ConcurrentHashMap<>();
    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean lock() {
        return lock();
    }

    @Override
    public boolean lock(Long timeout, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public void unlock() {
        Thread cur = Thread.currentThread();
        LockData lockData = lockMetaData.get(cur);
        if(null==lockData){
            throw new IllegalArgumentException("you do not own lock "+ lockPath);
        }
        int lockCount= lockData.count.incrementAndGet();
        if(lockCount>0){
            return ;
        }
        if(lockCount<0){
            throw new IllegalArgumentException("lockCount is negative");
        }
        try{

        }finally {
            lockMetaData.remove(cur);
        }
    }

    @Data
    @AllArgsConstructor
    private static class LockData{
       private AtomicInteger count;
       private Thread owner;
    }
}
