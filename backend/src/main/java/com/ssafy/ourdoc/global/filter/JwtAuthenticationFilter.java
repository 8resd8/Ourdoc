package com.ssafy.ourdoc.global.filter;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.util.JwtBlacklistService;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${prod.excluded-paths}")
	private String excludedPathsRaw;

	private final JwtUtil jwtUtil;
	private final JwtBlacklistService blacklistService;
	private final JwtRefreshService jwtRefreshService;

	private List<String> excludedPaths;

	@PostConstruct
	public void init() {
		excludedPaths = Arrays.asList(excludedPathsRaw.split(","));
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		return excludedPaths.stream().anyMatch(excluded -> (contextPath + excluded).equals(path));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		System.out.println("필터 진입");

		// Preflight 응답은 바로 통과
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = extractToken(request);

		// 블랙리스트에 있는 토큰이면 401 응답
		if (blacklistService.isBlacklisted(token)) {
			log.info("Unauthorized: Token is blacklisted {}", token);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is blacklisted");
			return;
		}

		// 토큰이 없으면 요청 차단
		if (token == null) {
			log.info("Unauthorized: Missing token");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing token");
			return;
		}

		try {
			Claims claims = jwtUtil.getClaims(token);
			String userId = claims.getSubject();
			String role = claims.get("role", String.class);

			request.setAttribute("userId", userId);
			request.setAttribute("role", role);

			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException e) {
			log.info("액세스 토큰 만료됨, Refresh token을 이용해 재발급 시도");
			String newAccessToken = handleTokenRefresh(request, response);

			if (newAccessToken != null) {
				log.info("새로운 액세스 토큰 발급 성공: {}", newAccessToken);
				response.setHeader("Authorization", "Bearer " + newAccessToken);

				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				log.warn("새로운 액세스 토큰 발급 실패, 유효하지 않은 리프레시 토큰");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid refresh token");
			}
		} catch (JwtException e) {
			log.info("Unauthorized: Invalid token {}", e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token");
		}
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private String handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String refreshToken = extractRefreshToken(request);

		// 리프레시 토큰이 없으면 null 반환
		if (refreshToken == null) {
			log.warn("Refresh token 없음");
			return null;
		}

		try {
			Claims claims = jwtUtil.getClaims(refreshToken);
			String userId = claims.getSubject();

			// 리프레시 토큰이 유효한지 검증
			if (!jwtRefreshService.isValidRefreshToken(userId, refreshToken)) {
				log.warn("유효하지 않은 refresh token for user {}", userId);
				return null;
			}

			// 새로운 액세스 토큰 발급
			String role = claims.get("role", String.class);
			String newAccessToken = jwtUtil.createToken(userId, role);

			response.setHeader("Authorization", "Bearer " + newAccessToken);

			return newAccessToken;
		} catch (Exception e) {
			return null;
		}
	}

	private String extractRefreshToken(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (var cookie : request.getCookies()) {
				if ("Refresh-Token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
