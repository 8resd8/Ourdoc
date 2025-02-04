package com.ssafy.ourdoc.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.access-expiration}")
	private long accessExpiration;

	@Value("${jwt.refresh-expiration}")
	private long refreshExpiration;

	@Bean
	public String getSecretKey() {
		return secretKey;
	}

	@Bean
	public long getAccessExpiration() {
		return accessExpiration;
	}

	@Bean
	public long getRefreshExpiration() {return refreshExpiration; }
}
