package com.xiaomi.demo.metrics.acutor;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class MyInfoIndicator implements InfoContributor {
//    @Autowired
//    private ServerProperties.Undertow undertow;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("name", "test");
//        builder.withDetail("undertow_current_thread_",undertow.getThreads().)
    }
}
