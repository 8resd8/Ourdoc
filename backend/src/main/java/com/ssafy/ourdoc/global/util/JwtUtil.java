package com.ssafy.ourdoc.global.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ssafy.ourdoc.global.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtConfig jwtConfig;

	// SecretKey 생성
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
	}

	// JWT 토큰 생성
	public String createToken(String userId, String role) {
		return Jwts.builder()
			.setSubject(userId) // 사용자 ID 저장
			.claim("role", role) // 사용자 권한 저장
			.setIssuedAt(new Date()) // 토큰 발급 시간
			.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessExpiration())) // 만료 시간
			.signWith(getSigningKey(), SignatureAlgorithm.HS256) // 서명 알고리즘
			.compact();
	}

	// JWT 토큰 검증
	public boolean validateToken(String token) {
		log.info("🔎 검증할 토큰: {}", token);
		Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token);  // ❌ 예외 발생 시 catch 하지 않음
		log.info("✅ 토큰 검증 성공!");
		return true;
	}

	// JWT 토큰에서 Claims 정보 추출
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	// JWT 토큰에서 사용자 ID 추출
	public String getUserIdFromToken(String token) {
		return getClaims(token).getSubject(); // subject에 저장된 사용자 ID 반환
	}

	// 요청 헤더에서 JWT 토큰 추출
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	// JWT Refresh Token 생성
	public String createRefreshToken(String userId, String role) {
		return Jwts.builder()
			.setSubject(userId)
			.claim("role", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration())) // Refresh 만료 시간
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

}
