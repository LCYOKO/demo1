package com.xiaomi.auth.shiro;

//
//public class CustomSessionManager extends DefaultWebSessionManager {
//
//    public static final String HEADER_TOKEN = "Authorization";
//
//    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
//
//    public CustomSessionManager() {
//        super();
//    }
//
//    @Override
//    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
//        String sessionId = WebUtils.toHttp(request).getHeader(HEADER_TOKEN);
//        if (!StringUtils.isEmpty(sessionId)) {
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
//            return sessionId;
//        } else {
//            return null;
//        }
//    }
//}
