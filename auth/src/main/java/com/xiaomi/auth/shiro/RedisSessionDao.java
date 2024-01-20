package com.xiaomi.auth.shiro;

//public class RedisSessionDao extends AbstractSessionDAO {
//
//    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
//
//    @Value("${session.redis.expireTime}")
//    private long expireTime;
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public RedisSessionDao(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//
//    private static final String Session_Key_Pre = "iot:shiro-session:";
//
//    @Override
//    protected Serializable doCreate(Session session) {
//        Serializable sessionId = this.generateSessionId(session);
//        this.assignSessionId(session, sessionId);
//        redisTemplate.opsForValue().set(getSessionKey(sessionId), session, expireTime, TimeUnit.SECONDS);
//        return sessionId;
//    }
//
//    @Override
//    protected Session doReadSession(Serializable sessionId) {
//        return sessionId == null ? null : (Session) redisTemplate.opsForValue().get(getSessionKey(sessionId));
//    }
//
//    @Override
//    public void update(Session session) throws UnknownSessionException {
//        if (session != null && session.getId() != null) {
//            session.setTimeout(expireTime * 1000);
//            redisTemplate.opsForValue().set(getSessionKey(session.getId()), session, expireTime, TimeUnit.SECONDS);
//        }
//    }
//
//    @Override
//    public void delete(Session session) {
//        if (session != null && session.getId() != null) {
//            redisTemplate.opsForValue().getOperations().delete(getSessionKey(session.getId()));
//        }
//    }
//
//    @Override
//    public Collection<Session> getActiveSessions() {
//        Set<Session> sessions = new HashSet<>();
//        try {
//            Set<String> keys = redisTemplate.keys(Session_Key_Pre + "*");
//            if (keys != null && keys.size() > 0) {
//                for (String key:keys) {
//                    Session s = (Session) redisTemplate.opsForValue().get(key);
//                    sessions.add(s);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("get active sessions error.", e);
//        }
//        return sessions;
//    }
//
//
//    private String getSessionKey(Serializable sessionId) {
//        return Session_Key_Pre + sessionId;
//    }
//
//}
