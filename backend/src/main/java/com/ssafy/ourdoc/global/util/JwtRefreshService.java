package com.ssafy.ourdoc.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRefreshService {

	private final RedisTemplate<String, String> redisTemplate;
	private static final String REFRESH_PREFIX = "refresh:";

	// Refresh Token 저장 (사용자가 로그인할 때 저장)
	public void storeRefreshToken(String userId, String refreshToken, long expirationMillis) {
		redisTemplate.opsForValue().set(REFRESH_PREFIX + userId, refreshToken, expirationMillis, TimeUnit.MILLISECONDS);
	}

	// 저장된 Refresh Token을 가져오기
	public String getRefreshToken(String userId) {
		return redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
	}

	// Refresh Token 검증
	public boolean isValidRefreshToken(String userId, String refreshToken) {
		String storedToken = redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
		return storedToken != null && storedToken.equals(refreshToken);
	}

	// Refresh Token 삭제 (로그아웃 또는 재발급 시)
	public void removeRefreshToken(String userId) {
		redisTemplate.delete(REFRESH_PREFIX + userId);
	}
}
