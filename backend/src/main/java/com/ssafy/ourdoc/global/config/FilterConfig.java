package com.ssafy.ourdoc.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ourdoc.global.filter.JwtAuthenticationFilter;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public FilterConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public FilterRegistrationBean<OncePerRequestFilter> jwtFilter() {
		FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtAuthenticationFilter);
		registrationBean.addUrlPatterns("/api/*"); // 보호할 URL 패턴 설정
		return registrationBean;
	}
}
