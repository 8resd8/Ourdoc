package com.ssafy.ourdoc.domain.user.teacher.dto;

public record TeacherNotInClassProfileDto(
	String profileImagePath,
	String name,
	String loginId,
	String email,
	String phone
) {
}
