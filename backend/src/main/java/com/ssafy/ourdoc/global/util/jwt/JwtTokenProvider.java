package com.ssafy.ourdoc.global.util.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ssafy.ourdoc.global.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final JwtConfig jwtConfig;

	public JwtTokenProvider(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	// SecretKey 생성 (JwtConfig에서 키 가져오기)
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}

	// JWT 토큰 생성
	public String createToken(String userId, String role) {
		return Jwts.builder()
			.setSubject(userId) // 사용자 ID
			.claim("role", role) // 사용자 권한
			.setIssuedAt(new Date()) // 발급 시간
			.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration())) // 만료 시간
			.signWith(getSigningKey(), SignatureAlgorithm.HS256) // 최신 방식: SecretKey + 알고리즘
			.compact();
	}

	// JWT 토큰에서 Claims 정보 추출
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey()) // 최신 방식: SecretKey 사용
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	// JWT 토큰 유효성 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getSigningKey()) // SecretKey 설정
				.build()
				.parseClaimsJws(token); // JWT 파싱
			return true; // 유효한 토큰
		} catch (JwtException | IllegalArgumentException e) {
			return false; // 유효하지 않은 토큰
		}
	}
}
