package com.ssafy.ourdoc.domain.user.student.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.annotation.EnumValid;
import com.ssafy.ourdoc.global.common.enums.Gender;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
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
	@Positive(message = "{positive}")
	Long schoolId,

	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long classId,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 20, message = "{size.max}")
	Integer grade,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{size.max}")
	Integer classNumber,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{size.max}")
	Integer studentNumber,

	@Past(message = "{past}")
	Date birth,

	@EnumValid(enumClass = Gender.class, message = "{not.enum}")
	Gender gender
) {
}
