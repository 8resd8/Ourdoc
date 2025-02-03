package com.ssafy.ourdoc.global.util;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ssafy.ourdoc.global.filter.JwtAuthenticationFilter;
import com.ssafy.ourdoc.global.util.JwtUtil;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

	private MockMvc mockMvc;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new JwtTestController()) // 테스트용 컨트롤러 추가
			.addFilter(jwtAuthenticationFilter) // 필터 적용
			.build();
	}

	@Test
	@DisplayName("✅ 유효한 토큰으로 요청 성공")
	void validTokenRequest_Success() throws Exception {
		// Given: 유효한 JWT 토큰
		String validToken = "Bearer valid.jwt.token";
		given(jwtUtil.validateToken("valid.jwt.token")).willReturn(true);

		// 📌 Claims Mocking 추가 (여기가 핵심)
		Claims mockClaims = mock(Claims.class);
		given(mockClaims.getSubject()).willReturn("testUser");
		given(mockClaims.get("role", String.class)).willReturn("ROLE_USER");
		given(jwtUtil.getClaims("valid.jwt.token")).willReturn(mockClaims);  // ✅ Claims 반환하도록 설정

		// When & Then: 요청 검증
		mockMvc.perform(get("/users/test")
				.header(HttpHeaders.AUTHORIZATION, validToken))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("❌ 토큰이 없는 요청 실패")
	void noTokenRequest_Failure() throws Exception {
		mockMvc.perform(get("/users/test"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("❌ 유효하지 않은 토큰으로 요청 실패")
	void invalidTokenRequest_Failure() throws Exception {
		// Given
		String invalidToken = "Bearer invalid.jwt.token";
		given(jwtUtil.validateToken("invalid.jwt.token")).willReturn(false);

		// When & Then
		mockMvc.perform(get("/users/test")
				.header(HttpHeaders.AUTHORIZATION, invalidToken))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("❌ 잘못된 토큰 형식으로 요청 실패")
	void malformedTokenRequest_Failure() throws Exception {
		// Given
		String malformedToken = "InvalidTokenFormat";

		// When & Then
		mockMvc.perform(get("/users/test")
				.header(HttpHeaders.AUTHORIZATION, malformedToken))
			.andExpect(status().isUnauthorized());
	}
}
