package com.ssafy.ourdoc.domain.user.dto;

import com.ssafy.ourdoc.global.annotation.EnumValid;
import com.ssafy.ourdoc.global.common.enums.UserType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
	@EnumValid(enumClass = UserType.class, message = "{not.enum}")
	UserType userType,  // "학생", "교사", "관리자"

	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String loginId,   // 로그인 ID

	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String password  // 비밀번호
) {
}
