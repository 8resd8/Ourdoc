package com.ssafy.ourdoc.domain.user.dto;

public record StudentQueryDto(
	String schoolName,
	int grade,
	int classNumber,
	int studentNumber
) {}
