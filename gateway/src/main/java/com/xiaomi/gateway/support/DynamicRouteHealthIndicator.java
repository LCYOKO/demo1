package com.xiaomi.gateway.support;

import com.xiaomi.gateway.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author project
 * @date 2020/11/19
 * <p>
 * 动态路由检查检查
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicRouteHealthIndicator extends AbstractHealthIndicator {

	private final RedisTemplate redisTemplate;

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		if (redisTemplate.hasKey(Constants.ROUTE_KEY)) {
			builder.up();
		}
		else {
			log.warn("动态路由监控检查失败，当前无路由配置");
			builder.down();
		}
	}

}
