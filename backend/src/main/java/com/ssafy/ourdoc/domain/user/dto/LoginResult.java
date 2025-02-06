package com.ssafy.ourdoc.domain.user.dto;

public record LoginResult(
	LoginResponse loginResponse,
	String accessToken
) {}