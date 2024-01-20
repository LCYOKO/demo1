package com.xiaomi.web.listener;

import com.xiaomi.web.core.listener.ServletRequestListener;
import com.xiaomi.web.core.listener.event.ServletRequestEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sinjinsong
 * @date 2018/5/3
 */
@Slf4j
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("request destroy...");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        log.info("request init...");
    }
}
