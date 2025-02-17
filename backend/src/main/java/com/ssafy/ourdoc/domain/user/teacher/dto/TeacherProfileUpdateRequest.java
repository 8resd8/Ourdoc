package com.ssafy.ourdoc.domain.user.teacher.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
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

	@Size(max = 250, message = "{size.max}")
	Long schoolId,

	@Max(value = 250, message = "{length.max}")
	Integer year,

	@Max(value = 250, message = "{length.max}")
	Integer grade,

	@Max(value = 250, message = "{length.max}")
	Integer classNumber
) {
}
