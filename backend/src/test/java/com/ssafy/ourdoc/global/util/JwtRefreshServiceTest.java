// package com.ssafy.ourdoc.global.util;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.concurrent.TimeUnit;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.redis.core.RedisTemplate;
//
// @SpringBootTest
// class JwtRefreshServiceTest {
//
// 	@Autowired
// 	private JwtRefreshService jwtRefreshService;
//
// 	@Autowired
// 	private JwtUtil jwtUtil;
//
// 	@Autowired
// 	private RedisTemplate<String, String> redisTemplate;
//
// 	private String userId;
// 	private String refreshToken;
//
// 	@BeforeEach
// 	void setUp() {
// 		userId = "testUser";
// 		refreshToken = jwtUtil.createRefreshToken(userId);
// 		jwtRefreshService.storeRefreshToken(userId, refreshToken, 60000); // 1분 저장
// 	}
//
// 	@Test
// 	@DisplayName("✅ Refresh Token이 Redis에 정상적으로 저장됨")
// 	void refreshTokenStoredInRedis() {
// 		String storedToken = jwtRefreshService.getRefreshToken(userId);
// 		assertThat(storedToken).isEqualTo(refreshToken);
// 	}
//
// 	@Test
// 	@DisplayName("✅ Refresh Token 검증 성공")
// 	void validateRefreshToken_Success() {
// 		assertThat(jwtRefreshService.isValidRefreshToken(userId, refreshToken)).isTrue();
// 	}
//
// 	@Test
// 	@DisplayName("❌ Refresh Token 만료 테스트")
// 	void refreshToken_TTL_Override_Expiration() throws InterruptedException {
// 		// ✅ 기존 1분 TTL 저장 (setup()에서 수행됨)
// 		jwtRefreshService.storeRefreshToken(userId, refreshToken, 60000);
//
// 		// ✅ 같은 Key에 대해 TTL을 1초로 덮어쓰기
// 		jwtRefreshService.storeRefreshToken(userId, refreshToken, 1000);
//
// 		// ✅ Redis TTL 확인
// 		Long ttlBefore = redisTemplate.getExpire("refresh:" + userId, TimeUnit.MILLISECONDS);
// 		System.out.println("🔍 변경된 TTL: " + ttlBefore + "ms");
//
// 		// ✅ 1.5초 후 Refresh Token 만료 확인
// 		Thread.sleep(1500);
// 		assertThat(jwtRefreshService.isValidRefreshToken(userId, refreshToken)).isFalse();
// 	}
//
// 	@Test
// 	@DisplayName("❌ Refresh Token 삭제 후 검증 실패")
// 	void removeRefreshToken_ShouldInvalidate() {
// 		jwtRefreshService.removeRefreshToken(userId);
// 		assertThat(jwtRefreshService.isValidRefreshToken(userId, refreshToken)).isFalse();
// 	}
// }
