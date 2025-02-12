package com.ssafy.ourdoc.domain.user.dto.response;

import com.ssafy.ourdoc.global.common.enums.UserType;

public record TeacherNotInClassLoginDto(
	String loginId,
	String name,
	UserType role,
	String profileImagePath
) {
}
