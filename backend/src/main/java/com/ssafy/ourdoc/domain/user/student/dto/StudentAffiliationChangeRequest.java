package com.ssafy.ourdoc.domain.user.student.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentAffiliationChangeRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
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
	Integer studentNumber
) {
}
