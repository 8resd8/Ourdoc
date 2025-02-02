package com.ssafy.ourdoc.global.util.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ssafy.ourdoc.global.config.JwtConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtConfig jwtConfig;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
	}

	// ✅ JWT 토큰 생성
	public String generateToken(String loginId) {
		return Jwts.builder()
			.setSubject(loginId) // 사용자 ID 저장
			.setIssuedAt(new Date()) // 토큰 발급 시간
			.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration())) // 만료 시간
			.signWith(getSigningKey(), SignatureAlgorithm.HS256) // 서명 알고리즘
			.compact();
	}

	// ✅ JWT 토큰 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			log.error("JWT 검증 실패: {}", e.getMessage());
			return false;
		}
	}

	// ✅ 토큰에서 사용자 ID 추출
	public String getLoginIdFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
