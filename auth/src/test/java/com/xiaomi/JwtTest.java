package com.xiaomi;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/17
 */
public class JwtTest {
    private static final String SECRET = "XIAOMI@123";

    @Test
    public void testCreateJwt() {
        DateTime dateTime = DateUtil.offset(new Date(), DateField.HOUR_OF_DAY, 10);
        String token = JWT.create()
                .withHeader(Collections.emptyMap())
                .withClaim("name", "lcy")
                .withClaim("age", 18)
                .withExpiresAt(dateTime.toJdkDate())
                .sign(Algorithm.HMAC256(SECRET));
        System.out.println(token);
    }

    @Test
    public void validateTokenTest() {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoibGN5IiwiZXhwIjoxNzA1NTk0OTkxLCJhZ2UiOjE4fQ.SqA_rBQX-tUjlDGE1z1i31DuKnN8U5I0zocl36Sii0k");
        System.out.println(decodedJWT.getClaims());
    }
}
