package com.ssafy.ourdoc.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ourdoc.global.interceptor.JwtInterceptor;
import com.ssafy.ourdoc.global.resolver.LoginArgumentResolver;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	@Value("${prod.url}")
	private String prodUrl;

	@Value("${prod.excluded-paths}")
	private String excludedPathsRaw;

	private final LoginArgumentResolver loginArgumentResolver;
	private final JwtInterceptor jwtInterceptor;
	private List<String> excludedPaths;

	@PostConstruct
	public void init() {
		excludedPaths = Arrays.asList(excludedPathsRaw.split(","));
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(excludedPaths.toArray(new String[0]));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5173", prodUrl)
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedHeaders("*")
			.exposedHeaders("Authorization")
			.allowCredentials(true);
	}

}

