package com.xiaomi.demo.cache;

import org.caffinitas.ohc.CacheSerializer;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

import java.nio.ByteBuffer;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/31
 * https://github.com/snazy/ohc
 */
public class LocalCacheTest {
    CacheSerializer<byte[]> valueSerializer = new CacheSerializer() {

        @Override
        public void serialize(Object o, ByteBuffer byteBuffer) {
//            byteBuffer.putInt(bytes.length);
//            byteBuffer.put(bytes);
        }

        @Override
        public byte[] deserialize(ByteBuffer byteBuffer) {
            byte[] arr = new byte[byteBuffer.getInt()];
            byteBuffer.get(arr);
            return arr;
        }

        @Override
        public int serializedSize(Object o) {
            return 0;
        }
    };

    OHCache<byte[], byte[]> ohcCache = OHCacheBuilder.<byte[], byte[]>newBuilder()
            .keySerializer(valueSerializer)
            .valueSerializer(valueSerializer)
            // number of segments (must be a power of 2), defaults to number-of-cores * 2
            .segmentCount(2)
            // hash table size (must be a power of 2), defaults to 8192
            .hashTableSize(1024)
            .capacity(2 * 1024 * 8L)
            .timeouts(true)
            // 每个段的超时插槽数
            .timeoutsSlots(64)
            // 每个timeouts-slot的时间量（以毫秒为单位）
            .timeoutsPrecision(512)
            // "LRU" 最旧的（最近最少使用的）条目被逐出
            // "W_TINY_LFU" 使用频率较低的条目被逐出，以便为新条目腾出空间
            .eviction(Eviction.W_TINY_LFU)
            .build();
}
