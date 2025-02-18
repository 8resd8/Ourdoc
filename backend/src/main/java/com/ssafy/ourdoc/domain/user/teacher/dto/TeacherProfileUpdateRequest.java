package com.ssafy.ourdoc.domain.user.teacher.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TeacherProfileUpdateRequest(
	@Size(max = 250, message = "{size.max}")
	String name,

	@Size(max = 250, message = "{size.max}")
	String loginId,

	@Email(message = "{email}")
	@Size(max = 250, message = "{size.max}")
	String email,

	@Size(max = 250, message = "{size.max}")
	String phone,

	@Positive(message = "{positive}")
	Long schoolId,

	@Min(value = 1900, message = "{size.min}")
	@Max(value = 2200, message = "{size.max}")
	Integer year,

	@Min(value = 1, message = "{size.min}")
	@Max(value = 6, message = "{size.max}")
	Integer grade,

	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{size.max}")
	Integer classNumber
) {
}
