package com.xiaomi.demo.java.net.netty.mqtt.common.auth;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;

/**
 * @author liuchiyun
 */
@Service
public class AuthServiceImpl implements GrozaAuthService {
    private RSAPrivateKey privateKey;

    @Override
    public boolean checkValid(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        RSA rsa = new RSA(privateKey, null);
        String value = rsa.encryptBcd(username, KeyType.PrivateKey);
        return value.equals(password) ? true : false;
    }

    @PostConstruct
    public void init() {
        privateKey = IoUtil.readObj(AuthServiceImpl.class.getClassLoader().getResourceAsStream("keystore/auth-private.key"));
    }
}
