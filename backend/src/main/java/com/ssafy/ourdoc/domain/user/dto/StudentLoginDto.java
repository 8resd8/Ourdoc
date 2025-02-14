package com.ssafy.ourdoc.domain.user.dto;

import com.ssafy.ourdoc.global.common.enums.TempPassword;
import com.ssafy.ourdoc.global.common.enums.UserType;

public record StudentLoginDto(
	String loginId,
	String name,
	UserType role,
	String schoolName,
	Long schoolId,
	Long classId,
	int grade,
	int classNumber,
	int studentNumber,
	TempPassword tempPassword,
	String ProfileImagePath
) {}
