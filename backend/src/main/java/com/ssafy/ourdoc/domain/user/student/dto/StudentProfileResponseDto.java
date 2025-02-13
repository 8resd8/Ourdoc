package com.ssafy.ourdoc.domain.user.student.dto;

import java.util.Date;

import com.ssafy.ourdoc.global.common.enums.Active;

public record StudentProfileResponseDto(
	String profileImage,
	String name,
	String loginId,
	String schoolName,
	int grade,
	int classNumber,
	int studentNumber,
	Date birth,
	Active active
) {
}
