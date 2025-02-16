package com.ssafy.ourdoc.global.util;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ssafy.ourdoc.global.filter.JwtAuthenticationFilter;
import com.ssafy.ourdoc.global.util.JwtBlacklistService;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtTestController;
import com.ssafy.ourdoc.global.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

	private MockMvc mockMvc;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private JwtBlacklistService blacklistService;

	@Mock
	private JwtRefreshService jwtRefreshService;

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new JwtTestController())
			.addFilter(jwtAuthenticationFilter)
			.build();

		// 🔥 @Value("${prod.excluded-paths}") 강제 설정
		String mockExcludedPaths = "/teachers/signup,/students/signup,/users/signin,/users/signout,/users/checkId,/debate/**,/openvidu/**";
		ReflectionTestUtils.setField(jwtAuthenticationFilter, "excludedPathsRaw", mockExcludedPaths);
		ReflectionTestUtils.invokeMethod(jwtAuthenticationFilter, "init");
	}

	@Test
	@DisplayName("✅ 유효한 액세스 토큰이 있으면 요청 성공")
	void validTokenRequest_Success() throws Exception {
		// Given
		String validToken = "Bearer valid.jwt.token";
		Claims mockClaims = mock(Claims.class);
		given(mockClaims.getSubject()).willReturn("testUser");
		given(mockClaims.get("role", String.class)).willReturn(학생.name());
		given(jwtUtil.getClaims("valid.jwt.token")).willReturn(mockClaims);
		given(blacklistService.isBlacklisted("valid.jwt.token")).willReturn(false);

		// When & Then
		mockMvc.perform(get("/users/test")
				.header(HttpHeaders.AUTHORIZATION, validToken))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("❌ 토큰이 없으면 요청 실패")
	void noTokenRequest_Failure() throws Exception {
		mockMvc.perform(get("/users/test"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("✅ 액세스 토큰이 만료되었지만 리프레시 토큰이 유효하면 새 액세스 토큰 발급")
	void expiredAccessToken_ShouldTriggerRefresh() throws Exception {
		// Given
		String expiredToken = "Bearer expired.jwt.token";
		String refreshToken = "valid.refresh.token";
		String newAccessToken = "new.jwt.token";

		// Mock JWT 검증
		given(blacklistService.isBlacklisted("expired.jwt.token")).willReturn(false);
		given(jwtUtil.getClaims("expired.jwt.token")).willThrow(new ExpiredJwtException(null, null, "Token expired"));

		Claims refreshClaims = mock(Claims.class);
		given(refreshClaims.getSubject()).willReturn("testUser");
		given(refreshClaims.get("role", String.class)).willReturn("USER");
		given(jwtUtil.getClaims(refreshToken)).willReturn(refreshClaims);
		given(jwtRefreshService.isValidRefreshToken("testUser", refreshToken)).willReturn(true);
		given(jwtUtil.createToken("testUser", "USER")).willReturn(newAccessToken);

		// 쿠키에 리프레시 토큰 추가
		Cookie refreshTokenCookie = new Cookie("Refresh-Token", refreshToken);

		// When & Then
		mockMvc.perform(get("/users/test")
				.header(HttpHeaders.AUTHORIZATION, expiredToken)
				.cookie(refreshTokenCookie))
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken));
	}
}
