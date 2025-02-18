package com.ssafy.ourdoc.domain.user.teacher.dto;

import java.util.Date;

public record TeacherNotInClassProfileDto(
	String profileImagePath,
	String name,
	String loginId,
	String email,
	String phone,
	Date birth
) {
}
