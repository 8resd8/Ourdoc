package com.ssafy.ourdoc.global.interceptor;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtUtil jwtUtil;
	private final JwtRefreshService jwtRefreshService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (CorsUtils.isPreFlightRequest(request)) {
			return true;
		}

		UserType role = UserType.valueOf((String)request.getAttribute("role"));
		String path = request.getRequestURI();

		if (role == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing user role");
			return false;
		}

		if (!isAuthorized(path, role)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Unauthorized user");
			return false;
		}

		return true;
	}

	private boolean isAuthorized(String path, UserType role) {

		if (path.startsWith("/api/admin") && !role.equals(관리자)) {
			return false;
		} else if ((path.startsWith("/api/teachers") || path.startsWith("/api/books/teachers") ||
			path.startsWith("/api/bookreports/teachers") || path.startsWith("/api/debates/teachers")) && !role.equals(교사)) {
			return false;
		} else if ((path.startsWith("/api/students") || path.startsWith("/api/books/students") ||
			path.startsWith("/api/bookreports/students") || path.startsWith("/api/debates/students")) && !role.equals(학생)) {
			return false;
		}

		return true;
	}
}
