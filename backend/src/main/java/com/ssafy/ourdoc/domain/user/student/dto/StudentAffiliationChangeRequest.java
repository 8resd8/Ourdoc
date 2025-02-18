package com.ssafy.ourdoc.domain.user.student.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record StudentAffiliationChangeRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String schoolName,

	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long schoolId,

	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long classId,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 6, message = "{size.max}")
	Integer grade,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{size.max}")
	Integer classNumber,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{size.max}")
	Integer studentNumber
) {
}
