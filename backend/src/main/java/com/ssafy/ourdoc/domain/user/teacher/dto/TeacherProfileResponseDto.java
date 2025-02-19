package com.ssafy.ourdoc.domain.user.teacher.dto;

import java.util.Date;

public record TeacherProfileResponseDto(
	String profileImagePath,
	String name,
	String loginId,
	String email,
	String schoolName,
	Long schoolId,
	int grade,
	int classNumber,
	String phone,
	Date birth
) {
}
