package com.ssafy.ourdoc.domain.user.student.dto;

import com.ssafy.ourdoc.global.common.enums.Active;

public record InactiveStudentProfileResponseDto(
	String profileImage,
	String name,
	String loginId,
	Active active
) {
}
