package com.ssafy.ourdoc.domain.user.student.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Gender;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentSignupRequest(
	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String name,

	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String loginId,

	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String password,

	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String schoolName,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Long schoolId,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Long classId,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Integer grade,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Integer classNumber,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Integer studentNumber,

	@Size(max = 250, message = "{size.max}")
	Date birth,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Gender gender
) {
}
