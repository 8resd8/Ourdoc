package com.ssafy.ourdoc.domain.user.dto;

public record StudentQueryDto(
	String schoolName,
	Long schoolId,
	int grade,
	int classNumber,
	int studentNumber
) {}
