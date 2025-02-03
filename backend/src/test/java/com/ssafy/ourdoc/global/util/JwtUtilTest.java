package com.ssafy.ourdoc.global.util;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ssafy.ourdoc.global.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

class JwtUtilTest {

	private JwtUtil jwtUtil;

	@Mock
	private JwtConfig jwtConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// JWT 설정값 모킹
		jwtConfig = new JwtConfig() {
			@Override
			public String getSecretKey() {
				return "this-is-a-very-secure-secret-key-used-for-jwt";
			}

			@Override
			public long getExpiration() {
				return 1000 * 60 * 60; // 1시간
			}
		};

		jwtUtil = new JwtUtil(jwtConfig);
	}

	@Test
	@DisplayName("유효한 토큰 생성 및 검증 성공")
	void createAndValidateToken_Success() {
		// Given
		String userId = "testUser";
		String role = "ROLE_USER";

		// When
		String token = jwtUtil.createToken(userId, role);
		boolean isValid = jwtUtil.validateToken(token);
		Claims claims = jwtUtil.getClaims(token);

		// Then
		assertThat(isValid).isTrue();
		assertThat(claims.getSubject()).isEqualTo(userId);
		assertThat(claims.get("role", String.class)).isEqualTo(role);
	}

	@Test
	@DisplayName("❌ 만료된 토큰 검증 실패")
	void expiredToken_ThrowsException() throws InterruptedException {
		// Given: 만료 시간이 1초인 토큰 생성
		String expiredToken = Jwts.builder()
			.setSubject("testUser")
			.claim("role", "ROLE_USER")
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000)) // 1초 뒤 만료
			.signWith(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();

		// 토큰이 만료되도록 대기
		Thread.sleep(2000);

		// When & Then: validateToken()이 ExpiredJwtException을 던지는지 확인
		assertThatThrownBy(() -> jwtUtil.validateToken(expiredToken))
			.isInstanceOf(ExpiredJwtException.class);
	}

	@Test
	@DisplayName("🚨 유효하지 않은 토큰 검증 시 예외 발생")
	void invalidToken_ThrowsException() {
		// Given
		String invalidToken = "invalid.jwt.token";  // 잘못된 형식의 JWT

		// When & Then: 예외 발생 검증
		assertThatThrownBy(() -> jwtUtil.validateToken(invalidToken))
			.isInstanceOf(io.jsonwebtoken.MalformedJwtException.class);
	}
}

