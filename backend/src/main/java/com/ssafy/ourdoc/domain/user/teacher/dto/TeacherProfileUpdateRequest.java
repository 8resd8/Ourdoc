package com.ssafy.ourdoc.domain.user.teacher.dto;

import jakarta.validation.constraints.Size;

public record TeacherProfileUpdateRequest(
	@Size(max = 250, message = "{size.max}")
	String name,
	
	@Size(max = 250, message = "{size.max}")

	@Size(max = 250, message = "{size.max}")
	String loginId,

	@Size(max = 250, message = "{size.max}")
	String email,

	@Size(max = 250, message = "{size.max}")
	String phone,

	@Size(max = 250, message = "{size.max}")
	Long schoolId,

	@Size(max = 250, message = "{size.max}")
	Integer year,

	@Size(max = 250, message = "{size.max}")
	Integer grade,

	@Size(max = 250, message = "{size.max}")
	Integer classNumber
) {
}
