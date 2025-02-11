package com.ssafy.ourdoc.global.filter;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.util.JwtBlacklistService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final JwtBlacklistService blacklistService;
	private final List<String> excludedPaths = List.of("/teachers/signup", "/students/signup",
		"/users/signin", "/users/checkId");

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		return excludedPaths.stream().anyMatch(excluded -> (contextPath + excluded).equals(path));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String token = extractToken(request);

		// 블랙리스트에 있는 토큰이면 401 응답
		if (blacklistService.isBlacklisted(token)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is blacklisted");
			return;
		}

		// 토큰이 없거나, 유효하지 않다면 401 반환
		if (token == null || !jwtUtil.validateToken(token)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
			return;
		}

		try {
			Claims claims = jwtUtil.getClaims(token);
			String userId = claims.getSubject();
			UserType role = UserType.valueOf(claims.get("role", String.class));

			if (!isAuthorized(request.getRequestURI(), role)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "인가되지 않은 사용자입니다.");
				return;
			}

			// JWT에서 추출한 정보를 Request 속성으로 저장
			request.setAttribute("userId", userId);
			request.setAttribute("role", role);

			// 다음 필터로 요청을 전달
			filterChain.doFilter(request, response);

		} catch (JwtException e) {
			// JWT 검증 실패 시 401 반환
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token");
		}
	}

	private boolean isAuthorized(String path, UserType role) {
		boolean authorized = true;

		if (path.startsWith("/admin") && !role.equals(관리자)) {
			authorized = false;
		} else if ((path.startsWith("/teachers") || path.startsWith("/books/teachers") ||
			path.startsWith("/bookreports/teachers")) && !role.equals(교사)) {
			authorized = false;
		} else if ((path.startsWith("/students") || path.startsWith("/books/students") ||
			path.startsWith("/bookreports/students")) && !role.equals(학생)) {
			authorized = false;
		}

		return authorized;
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
