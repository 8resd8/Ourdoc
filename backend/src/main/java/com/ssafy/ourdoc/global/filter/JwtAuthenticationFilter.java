package com.ssafy.ourdoc.global.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return path.startsWith("/users/signin") || path.startsWith("/teachers/signup") || path.startsWith("/students/signup");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String token = extractToken(request);

		// 토큰이 없거나, 유효하지 않다면 401 반환
		if (token == null || !jwtUtil.validateToken(token)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
			return;
		}

		try {
			Claims claims = jwtUtil.getClaims(token);
			String userId = claims.getSubject();
			String role = claims.get("role", String.class);

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

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
