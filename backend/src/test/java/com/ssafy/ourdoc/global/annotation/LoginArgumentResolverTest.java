package com.ssafy.ourdoc.global.annotation;

import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.annotation.Login;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.resolver.LoginArgumentResolver;
import com.ssafy.ourdoc.global.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginArgumentResolverTest {

	private UserRepository userRepository;
	private JwtUtil jwtUtil;
	private LoginArgumentResolver resolver;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		jwtUtil = mock(JwtUtil.class);
		resolver = new LoginArgumentResolver(userRepository, jwtUtil);
	}

	@Test
	void resolveArgument_ValidToken_ReturnsUser() throws Exception {
		// Given
		String mockToken = "mockToken";
		String userLoginId = "testUser";

		// Mock User 생성
		User mockUser = UserSample.user(UserType.학생);

		// HttpServletRequest Mock 설정
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(jwtUtil.resolveToken(request)).thenReturn(mockToken);
		when(jwtUtil.getUserIdFromToken(mockToken)).thenReturn(userLoginId);
		when(userRepository.findByLoginId(userLoginId)).thenReturn(Optional.of(mockUser));

		NativeWebRequest webRequest = new ServletWebRequest(request);

		Method method = TestController.class.getDeclaredMethod("testMethod", User.class);
		MethodParameter methodParameter = new MethodParameter(method, 0);

		// When
		Object resolvedArgument = resolver.resolveArgument(methodParameter, new ModelAndViewContainer(), webRequest,
			null);

		// Then
		assertEquals(mockUser, resolvedArgument);
	}

	@Test
	void resolveArgument_InvalidToken_ReturnsNull() throws Exception {
		// Given
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(jwtUtil.resolveToken(request)).thenReturn(null);  // 토큰이 없는 경우

		NativeWebRequest webRequest = new ServletWebRequest(request);
		Method method = TestController.class.getDeclaredMethod("testMethod", User.class);
		MethodParameter methodParameter = new MethodParameter(method, 0);

		// When
		Object resolvedArgument = resolver.resolveArgument(methodParameter, new ModelAndViewContainer(), webRequest,
			null);

		// Then
		assertEquals(null, resolvedArgument);
	}

	private static class TestController {
		public void testMethod(@Login User user) {
			System.out.println(user.getUserType());
		}
	}
}
