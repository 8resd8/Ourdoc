package com.ssafy.ourdoc.domain.user.teacher.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeacherSignupRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String name,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String loginId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String password,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Date birth,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Gender gender,

	@Email(message = "{email}")
	@Size(max = 250, message = "{size.max}")
	String email,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}") // 정규식 필요
	String phone
) {
}
