package com.ssafy.ourdoc.domain.user.student.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Gender;

import jakarta.validation.constraints.NotBlank;
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

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long schoolId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long classId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Integer grade,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Integer classNumber,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Integer studentNumber,

	@Size(max = 250, message = "{size.max}")
	Date birth,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Gender gender
) {
}
