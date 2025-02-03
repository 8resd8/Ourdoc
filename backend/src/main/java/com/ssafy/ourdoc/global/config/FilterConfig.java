package com.ssafy.ourdoc.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ourdoc.global.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig implements WebMvcConfigurer {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtAuthenticationFilter);
		registrationBean.addUrlPatterns("/*"); // 보호할 URL 패턴 설정
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
