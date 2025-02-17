package com.ssafy.ourdoc.domain.user.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record TeacherVerificationRequest(
	@NotNull(message = "{notblank}")
	Boolean isApproved,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Long teacherId
) {
}
