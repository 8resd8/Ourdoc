package com.ssafy.ourdoc.domain.user.dto;

public record TeacherQueryDto(
	String schoolName,
	Long schoolId,
	Long classId,
	int grade,
	int classNumber
) {}