// package com.ssafy.ourdoc.global.util;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.nio.charset.StandardCharsets;
// import java.util.Date;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
//
// import com.ssafy.ourdoc.global.config.JwtConfig;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
//
// class JwtUtilTest {
//
// 	private JwtUtil jwtUtil;
//
// 	@Mock
// 	private JwtConfig jwtConfig;
//
// 	@BeforeEach
// 	void setUp() {
// 		MockitoAnnotations.openMocks(this);
//
// 		// JWT 설정값 모킹
// 		jwtConfig = new JwtConfig() {
// 			@Override
// 			public String getSecretKey() {
// 				return "this-is-a-very-secure-secret-key-used-for-jwt";
// 			}
//
// 			@Override
// 			public long getAccessExpiration() {
// 				return 1000 * 60 * 60; // 1시간
// 			}
//
// 			@Override
// 			public long getRefreshExpiration() {
// 				return 1000 * 60 * 60 * 24 * 7; // 7일
// 			}
// 		};
//
// 		jwtUtil = new JwtUtil(jwtConfig);
// 	}
//
// 	@Test
// 	@DisplayName("✅ 유효한 Access Token 생성 및 검증 성공")
// 	void createAndValidateAccessToken_Success() {
// 		// Given
// 		String userId = "testUser";
// 		String role = "ROLE_USER";
//
// 		// When
// 		String token = jwtUtil.createToken(userId, role);
// 		boolean isValid = jwtUtil.validateToken(token);
// 		Claims claims = jwtUtil.getClaims(token);
//
// 		// Then
// 		assertThat(isValid).isTrue();
// 		assertThat(claims.getSubject()).isEqualTo(userId);
// 		assertThat(claims.get("role", String.class)).isEqualTo(role);
// 	}
//
// 	@Test
// 	@DisplayName("✅ 유효한 Refresh Token 생성 및 검증 성공")
// 	void createAndValidateRefreshToken_Success() {
// 		// Given
// 		String userId = "testUser";
//
// 		// When
// 		String token = jwtUtil.createRefreshToken(userId);
// 		boolean isValid = jwtUtil.validateToken(token);
// 		Claims claims = jwtUtil.getClaims(token);
//
// 		// Then
// 		assertThat(isValid).isTrue();
// 		assertThat(claims.getSubject()).isEqualTo(userId);
// 	}
//
// 	@Test
// 	@DisplayName("❌ 만료된 Access Token 검증 실패")
// 	void expiredAccessToken_ThrowsException() throws InterruptedException {
// 		// Given: 만료 시간이 1초인 토큰 생성
// 		String expiredToken = Jwts.builder()
// 			.setSubject("testUser")
// 			.claim("role", "ROLE_USER")
// 			.setIssuedAt(new Date(System.currentTimeMillis()))
// 			.setExpiration(new Date(System.currentTimeMillis() + 1000)) // 1초 후 만료
// 			.signWith(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
// 			.compact();
//
// 		Thread.sleep(2000); // 토큰 만료 대기
//
// 		// When & Then
// 		assertThatThrownBy(() -> jwtUtil.validateToken(expiredToken))
// 			.isInstanceOf(ExpiredJwtException.class);
// 	}
//
// 	@Test
// 	@DisplayName("🚨 잘못된 JWT 토큰 검증 시 예외 발생")
// 	void invalidToken_ThrowsException() {
// 		// Given
// 		String invalidToken = "invalid.jwt.token";
//
// 		// When & Then
// 		assertThatThrownBy(() -> jwtUtil.validateToken(invalidToken))
// 			.isInstanceOf(io.jsonwebtoken.MalformedJwtException.class);
// 	}
// }
//
//
