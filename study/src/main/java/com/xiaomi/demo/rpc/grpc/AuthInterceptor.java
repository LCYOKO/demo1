package com.xiaomi.demo.rpc.grpc;

import cn.hutool.jwt.Claims;
import com.aliyuncs.auth.AuthConstant;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/23
 */
public class AuthInterceptor implements ServerInterceptor {
    private JwtParser parser = Jwts.parser().setSigningKey(AuthConstant.JWT_KEY);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authorization = metadata.get(Metadata.Key.of(AuthConstant.AUTH_HEADER, Metadata.ASCII_STRING_MARSHALLER));
        Status status = Status.OK;
        if (authorization == null) {
            status = Status.UNAUTHENTICATED.withDescription("miss authentication token");
        } else if (!authorization.startsWith(AuthConstant.AUTH_TOKEN_TYPE)) {
            status = Status.UNAUTHENTICATED.withDescription("unknown token type");
        } else {
            Jws<Claims> claims = null;
            String token = authorization.substring(AuthConstant.AUTH_TOKEN_TYPE.length()).trim();
            try {
                claims = parser.parseClaimsJws(token);
            } catch (JwtException e) {
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
            if (claims != null) {
                Context ctx = Context.current()
                        .withValue(AuthConstant.AUTH_CLIENT_ID, claims.getBody().getSubject());
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            }
        }
        serverCall.close(status, new Metadata());
        return new ServerCall.Listener<ReqT>() {
        };
    }
}
