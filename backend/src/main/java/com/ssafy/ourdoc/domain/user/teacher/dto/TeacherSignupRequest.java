package com.ssafy.ourdoc.domain.user.teacher.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.annotation.EnumValid;
import com.ssafy.ourdoc.global.common.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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

	@Past(message = "{past}")
	Date birth,

	@EnumValid(enumClass = Gender.class, message = "{not.enum}")
	Gender gender,

	@Email(message = "{email}")
	@Size(max = 250, message = "{size.max}")
	String email,

	@Size(max = 250, message = "{size.max}")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "{phone.number}")
	String phone
) {
}
