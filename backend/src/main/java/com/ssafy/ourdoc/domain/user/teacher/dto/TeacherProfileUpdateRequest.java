package com.ssafy.ourdoc.domain.user.teacher.dto;

public record TeacherProfileUpdateRequest(
	String name,
	String loginId,
	String email,
	String phone,
	Long schoolId,
	Integer year,
	Integer grade,
	Integer classNumber
) {
}
