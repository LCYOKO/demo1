package com.xiaomi.demo.security.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/17
 */
@Slf4j
public class EncryptTest {
    private String password = "123";

    @Test
    public void testMd5() {
        //MD5
        String hex = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("encryption:{}", hex);
    }

    @Test
    public void testBCrypt() {
        // 推荐使用BCryptPasswordEncoder
//      BcryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testAesECB() {
    }

    @Test
    public void testAesCBC() {
        // AES
        // CBC模式
        // 128位加密
        // 128位解密
        // 128位密钥
        // 128位偏移量
        // 128位密钥+128位偏移量
        // 128
    }

    @Test
    public void testAesGCM() {
        // AES
        // GCM模式
        // 128位加密
        // 128位解密
        // 128位密钥
        // 128位偏移量
    }
}
