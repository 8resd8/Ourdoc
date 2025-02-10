package com.ssafy.ourdoc.domain.user.teacher.dto;

public record TeacherProfileResponseDto(
	String profileImagePath,
	String name,
	String loginId,
	String email,
	String schoolName,
	int grade,
	int classNumber,
	String phone
) {
}
