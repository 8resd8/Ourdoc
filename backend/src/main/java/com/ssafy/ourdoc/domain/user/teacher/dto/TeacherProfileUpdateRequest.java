package com.ssafy.ourdoc.domain.user.teacher.dto;

public record TeacherProfileUpdateRequest(
	String name,
	String loginId,
	String email,
	String phone,
	String schoolName,
	String address,
	int year,
	int grade,
	int classNumber
) {
}
