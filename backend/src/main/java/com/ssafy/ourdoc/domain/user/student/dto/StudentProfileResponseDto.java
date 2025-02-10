package com.ssafy.ourdoc.domain.user.student.dto;

import com.ssafy.ourdoc.global.common.enums.Active;

public record StudentProfileResponseDto(
	String profileImage,
	String name,
	String loginId,
	String schoolName,
	int grade,
	int classNumber,
	int studentNumber,
	Active active
) {
}
