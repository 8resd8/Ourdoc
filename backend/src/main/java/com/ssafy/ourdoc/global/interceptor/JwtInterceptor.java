package com.ssafy.ourdoc.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

		String accessToken = extractToken(request);

		if (accessToken == null) {
			log.warn("토큰문제 발생, Access token: null");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing access token");
			return false;
		}

		try {
			jwtUtil.validateToken(accessToken);
			return true;
		} catch (ExpiredJwtException e) {
			log.info("액세스토큰 만료, Refresh token으로 재발급");
			return handleTokenRefresh(request, response);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid access token");
			return false;
		}
	}

	private boolean handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String refreshToken = extractRefreshToken(request);

		if (refreshToken == null) {
			log.warn("Refresh token: null");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing refresh token");
			return false;
		}

		try {
			Claims claims = jwtUtil.getClaims(refreshToken);
			String userId = claims.getSubject();

			if (!jwtRefreshService.isValidRefreshToken(userId, refreshToken)) {
				log.warn("Invalid refresh token for user {}", userId);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid refresh token");
				return false;
			}

			// Role이 없으면 기본값 "USER"로 설정
			String role = claims.get("role", String.class);
			if (role == null) {
				role = "USER";  // 기본 역할 설정
			}

			log.info("Creating new access token for user: {} with role: {}", userId, role);
			String newAccessToken = jwtUtil.createToken(userId, role);

			if (newAccessToken == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating new access token");
				return false;
			}

			// 새 Access Token 응답 헤더에 추가
			response.setHeader("Authorization", "Bearer " + newAccessToken);
			return true;

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid refresh token");
			return false;
		}
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		return (bearerToken != null && bearerToken.startsWith("Bearer "))
			? bearerToken.substring(7)
			: null;
	}

	private String extractRefreshToken(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (var cookie : request.getCookies()) {
				if (cookie.getName().equals("Refresh-Token")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
