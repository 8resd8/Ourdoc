package com.ssafy.ourdoc.domain.user.dto;

import com.ssafy.ourdoc.global.common.enums.TempPassword;
import com.ssafy.ourdoc.global.common.enums.UserType;

public record TeacherLoginDto(
	String loginId,
	String name,
	UserType role,
	String schoolName,
	Integer grade,
	Integer classNumber,
	String profileImagePath
) {}
