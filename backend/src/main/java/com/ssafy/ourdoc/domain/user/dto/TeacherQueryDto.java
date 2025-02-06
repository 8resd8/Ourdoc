package com.ssafy.ourdoc.domain.user.dto;

public record TeacherQueryDto(
	String schoolName,
	int grade,
	int classNumber
) {}