package com.ssafy.ourdoc.global.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ssafy.ourdoc.global.interceptor.JwtInterceptor;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

@SpringBootTest
class JwtInterceptorTest {

	private JwtInterceptor jwtInterceptor;
	private JwtUtil jwtUtil;
	private JwtRefreshService jwtRefreshService;

	private String userId;
	private String expiredAccessToken;
	private String refreshToken;
	private String newAccessToken;
	private SecretKey secretKey;

	@BeforeEach
	void setUp() {
		jwtUtil = mock(JwtUtil.class);
		jwtRefreshService = mock(JwtRefreshService.class);
		jwtInterceptor = new JwtInterceptor(jwtUtil, jwtRefreshService);

		userId = "testUser";
		secretKey = Keys.hmacShaKeyFor("this-is-a-secret-key-this-is-a-secret-key".getBytes(StandardCharsets.UTF_8));

		// ✅ 만료된 Access Token (이미 만료됨)
		expiredAccessToken = Jwts.builder()
			.setSubject(userId)
			.claim("role", "USER")
			.setIssuedAt(new Date(System.currentTimeMillis() - 60000))
			.setExpiration(new Date(System.currentTimeMillis() - 1000)) // 1초 전에 만료
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();

		// ✅ Refresh Token (2분 후 만료)
		refreshToken = Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 120000)) // 2분 후 만료
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();

		// ✅ 새 Access Token
		newAccessToken = "newAccessToken";
	}

	@Test
	@DisplayName("✅ Access Token 만료 시 Refresh Token으로 새 Access Token을 발급해야 함")
	void expiredAccessToken_ShouldTriggerRefresh() throws Exception {
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpServletResponse mockResponse = mock(HttpServletResponse.class);

		when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + expiredAccessToken);
		Cookie refreshTokenCookie = new Cookie("Refresh-Token", refreshToken);
		when(mockRequest.getCookies()).thenReturn(new Cookie[]{ refreshTokenCookie });

		doThrow(new ExpiredJwtException(null, null, "Token expired")).when(jwtUtil).validateToken(expiredAccessToken);

		Claims refreshTokenClaims = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(refreshToken)
			.getBody();
		when(jwtUtil.getClaims(refreshToken)).thenReturn(refreshTokenClaims);

		when(jwtRefreshService.isValidRefreshToken(userId, refreshToken)).thenReturn(true);

		// ✅ 디버깅 코드 추가: role 값 확인
		System.out.println("[TEST] userId: " + userId);
		System.out.println("[TEST] Role: " + refreshTokenClaims.get("role"));  // null이 정상
		System.out.println("[TEST] Refresh Token isValid: " + jwtRefreshService.isValidRefreshToken(userId, refreshToken));

		// ✅ role이 null이어도 "USER"가 기본값으로 들어가야 함
		when(jwtUtil.createToken(eq(userId), eq("USER"))).thenReturn(newAccessToken);

		// ✅ 인터셉터 실행
		boolean result = jwtInterceptor.preHandle(mockRequest, mockResponse, new Object());

		// ✅ 응답 헤더에 새 Access Token이 추가되었는지 확인
		verify(mockResponse).setHeader(eq("Authorization"), eq("Bearer " + newAccessToken));

		assertThat(result).isTrue();
	}
}
