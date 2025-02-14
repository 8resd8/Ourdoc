package com.ssafy.ourdoc.domain.user.dto;

public record StudentQueryDto(
	String schoolName,
	Long schoolId,
	Long classId,
	int grade,
	int classNumber,
	int studentNumber
) {}
