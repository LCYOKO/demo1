package com.xiaomi.demo.acutor;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class MyInfoIndicator implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("name", "test");
    }
}