package com.xiaomi;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.junit.Test;

import java.util.Date;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/17
 */
public class JwtTest {
    private static final String SECRET = "xiaomi";

    @Test
    public void testCreateJwt() {
        DateTime dateTime = DateUtil.offset(new Date(), DateField.HOUR_OF_DAY, 10);
        String token = JWT.create()
                .withClaim("name", "lcy")
                .withClaim("age", 18)
//                .withPayload(Maps.of("role", new Role("admin", "管理员", new String[]{"add", "delete"})))
                .withExpiresAt(dateTime.toJdkDate())
                .sign(Algorithm.HMAC256(SECRET));
        System.out.println(token);
    }

    @Test
    public void validateTokenTest() {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJZCI6MSwiTmFtZSI6Imxpc2kiLCJpc3MiOiJsaXVjaGl5dW4iLCJleHAiOjE3MjMyOTk2NDF9.8DdSZMuNHGG48ND31Vq1oLBG06MkvVES-5ulOiHIEO8");
        System.out.println(decodedJWT.getClaims());
    }

    @Data
    public static class Role {
        private String name;
        private String desc;
        private String[] permissions;

        public Role(String name, String desc, String[] permissions) {
            this.name = name;
            this.desc = desc;
            this.permissions = permissions;
        }

        public String getName() {
            return name;
        }

        public static Role of(String name, String desc, String[] permissions) {
            return new Role(name, desc, permissions);
        }
    }
}
