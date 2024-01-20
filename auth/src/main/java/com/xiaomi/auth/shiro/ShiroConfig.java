package com.xiaomi.auth.shiro;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
//@Configuration
//@ConditionalOnProperty(value = "shiro.enabled", havingValue = "true")
//public class ShiroConfig {
//
//    @Value("${session.redis.expireTime}")
//    private long expireTime;
//
//
////    @Bean(name = "customRealm")
////    public CustomRealm customRealm() {
////        return new CustomRealm();
////    }
//
//    @Bean(name = "redisSessionDao")
//    public RedisSessionDao redisSessionDao(@Qualifier("shiroRedisTemplate") RedisTemplate<String, Object> shiroRedisTemplate) {
//        return new RedisSessionDao(shiroRedisTemplate);
//    }
//
//    @Bean(name = "sessionManager")
//    public SessionManager sessionManager(RedisSessionDao redisSessionDao) {
//        CustomSessionManager customSessionManager = new CustomSessionManager();
//        customSessionManager.setSessionIdCookieEnabled(false);
//        customSessionManager.setSessionDAO(redisSessionDao);
//        customSessionManager.setGlobalSessionTimeout(expireTime * 1000);
//        customSessionManager.setSessionValidationSchedulerEnabled(true);
//        customSessionManager.setDeleteInvalidSessions(true);
//        return customSessionManager;
//    }
//
//    @Bean(name = "securityManager")
//    public CustomWebSecurityManager customWebSecurityManager(SessionManager sessionManager) {
//        CustomWebSecurityManager securityManager = new CustomWebSecurityManager();
//        securityManager.setRememberMeManager(null);
////        securityManager.setRealm(customRealm());
//        securityManager.setSessionManager(sessionManager);
//        return securityManager;
//    }
//
//    @Bean(name = "lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean(name = "shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        return shiroFilterFactoryBean;
//    }
//
//    @Bean
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        advisorAutoProxyCreator.setUsePrefix(true);
//        return advisorAutoProxyCreator;
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//}
