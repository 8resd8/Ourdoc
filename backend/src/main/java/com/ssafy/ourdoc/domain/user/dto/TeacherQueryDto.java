package com.ssafy.ourdoc.domain.user.dto;

public record TeacherQueryDto(
	String schoolName,
	Long schoolId,
	int grade,
	int classNumber
) {}