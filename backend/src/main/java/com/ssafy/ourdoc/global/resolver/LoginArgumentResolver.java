package com.ssafy.ourdoc.global.resolver;

import java.util.NoSuchElementException;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.annotation.Login;
import com.ssafy.ourdoc.global.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isLoginAnnotation = parameter.getParameterAnnotation(Login.class) != null;
		boolean isUser = parameter.getParameterType().equals(User.class);

		return isLoginAnnotation && isUser;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

		String token = jwtUtil.resolveToken(request);

		if (token == null) {
			return null;
		}

		String userLoginId = jwtUtil.getUserIdFromToken(token);
		return userRepository.findByLoginId(userLoginId)
			.orElseThrow(() -> new NoSuchElementException("해당 유저가 없음")); // 로그인할 때 ID, Password 검증하므로 바로 꺼냄
	}
}