package com.ssafy.ourdoc.domain.user.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentAffiliationChangeRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String schoolName,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long schoolId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long classId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	int grade,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	int classNumber,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	int studentNumber
) {
}
