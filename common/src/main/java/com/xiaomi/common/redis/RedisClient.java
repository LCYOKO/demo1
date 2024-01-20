package com.xiaomi.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/16
 */
public class RedisClient {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认过期时间
     */
    private static final long DEFAULT_TIME_OUT = 10;

    /**
     * 默认过期时间, 单位:s
     */
    private final static int DEFAULT_EXPIRE_TIME = 10 * 60 * 60;

    private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MINUTES;

    public RedisClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(@Nonnull String key, @Nullable Object value) {
        set(key, value, DEFAULT_TIME_OUT, DEFAULT_TIMEUNIT);
    }

    public void set(@Nonnull String key, @Nullable Object value, long timeout, @Nonnull TimeUnit timeUnit) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value, timeout, timeUnit);
    }

    public Object get(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void delete(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.getAndDelete(key);
    }

    public Long inc(String key) {
        return inc(key, 1L);
    }

    public Long inc(String key, long delta) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        return opsForValue.increment(key, delta);
    }
}
