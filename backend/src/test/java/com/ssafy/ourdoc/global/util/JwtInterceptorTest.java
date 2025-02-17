package com.ssafy.ourdoc.global.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.interceptor.JwtInterceptor;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtInterceptorTest {

	private JwtInterceptor jwtInterceptor;
	private JwtUtil jwtUtil;
	private JwtRefreshService jwtRefreshService;

	@BeforeEach
	void setUp() {
		jwtUtil = mock(JwtUtil.class);
		jwtRefreshService = mock(JwtRefreshService.class);
		jwtInterceptor = new JwtInterceptor(jwtUtil, jwtRefreshService);
	}

	@Test
	@DisplayName("✅ 유효한 역할(Role)이 설정되었을 때 요청 성공")
	void validRole_ShouldPass() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getRequestURI()).thenReturn("/teachers/class");
		when(request.getAttribute("role")).thenReturn(UserType.교사.name());

		boolean result = jwtInterceptor.preHandle(request, response, new Object());

		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("❌ 권한이 없는 사용자가 접근하면 요청 실패")
	void unauthorizedRole_ShouldFail() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getRequestURI()).thenReturn("/admin/dashboard");
		when(request.getAttribute("role")).thenReturn(UserType.학생.name());

		boolean result = jwtInterceptor.preHandle(request, response, new Object());

		assertThat(result).isFalse();
		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Unauthorized user");
	}
}
