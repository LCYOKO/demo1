package com.xiaomi.demo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "user")
public class UserProperties {
    private String name;
    private Integer age;
}
