package com.ssafy.ourdoc.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtBlacklistService {

	private final RedisTemplate<String, String> redisTemplate;
	private static final String BLACKLIST_PREFIX = "blacklist:";

	public void addToBlacklist(String token, long expirationMillis) {
		redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "blacklisted", expirationMillis, TimeUnit.MILLISECONDS);
	}

	public boolean isBlacklisted(String token) {
		return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
	}
}
