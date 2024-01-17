package com.xiaomi.auth.shiro;


//public class CustomRealm extends AuthorizingRealm {
//
//    @Resource
//    private RedisClient redisClient;
//
//    @Resource
//    private UserService userService;
//
//    /**
//     * Shiro 的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
//     * 当访问到页面的时候，链接配置了相应的权限或者 Shiro 标签才会执行此方法否则不会执行，
//     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回 null 即可。
//     *
//     * @param principalCollection 当前登录的唯一标识，具体是啥可以查看 doGetAuthenticationInfo返回的{@link AuthenticationInfo }
//     * @return authorizationInfo  用户授权信息(如角色，权限等)
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return new SimpleAuthorizationInfo();
//    }
//
//    /**
//     * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。
//     * 因为在 Shiro 中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
//     * 通常情况下，在 Realm 中会直接从我们的数据源中获取 Shiro 需要的验证信息。
//     * 可以说，Realm 是专用于安全框架的 DAO. Shiro 的认证过程最终会交由 Realm 执行，
//     * 这时会调用 Realm 的getAuthenticationInfo(token)方法。
//     * <p>
//     * 该方法主要执行以下操作:
//     * <p>
//     * 1、检查提交的进行认证的令牌信息
//     * 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
//     * 3、对用户信息进行匹配验证。
//     * 4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
//     * 5、验证失败则抛出AuthenticationException异常信息。
//     *
//     * @param authenticationToken 用户登录验证token
//     * @return authenticationInfo 用户登录验证相关的信息
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
//            throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        String account = token.getUsername();
//        User user = userService.getByAccount(account);
//        if (user == null) {
//            throw new AccountException("账号或密码有误");
//        }
//        if (user.getLockFlag() == 1) {
//            throw new AccountException("您的账户已被停用,请向系统管理员咨询");
//        }
//        String passwordInToken = new String(token.getPassword());
//        String passwordInDb = user.getPassword();
//        String encryptedPwd = Md5Utils.encryptPassword(passwordInToken);
//        Integer lockTimeValue = (Integer) redisClient.get(RedisKey.getLoginFailedTimesKey(account));
//        if (!passwordInDb.equals(encryptedPwd)) {
////            redisClient.inc(RedisKey.getLoginFailedTimesKey(account));
//            throw new AccountException("账号或密码有误");
//        } else {
//            if (lockTimeValue != null) {
////                redisClient.delete(RedisKey.getLoginFailedTimesKey(account));
//            }
//        }
//        return new SimpleAuthenticationInfo(user.getId(), passwordInToken, ByteSource.Util.bytes("salt"), getName());
//    }
//}