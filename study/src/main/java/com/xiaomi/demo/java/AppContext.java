package com.xiaomi.demo.java;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/15
 */
@Slf4j
@Data
public class AppContext {
    private static final TransmittableThreadLocal<AppContext> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    private String flag;


    public static AppContext getContext() {
        return TRANSMITTABLE_THREAD_LOCAL.get();
    }

    public static void setContext(AppContext context) {
        //判断标记
        if (StringUtils.isNotEmpty(context.getFlag())) {
            MDC.put("dunshan", "shadow");
        } else {
            MDC.put("dunshan", "produce");
        }
        TRANSMITTABLE_THREAD_LOCAL.set(context);
    }

    public static void removeContext() {
        TRANSMITTABLE_THREAD_LOCAL.remove();
    }
}
