package com.ssafy.ourdoc.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TeacherVerificationRequest(
	@NotNull(message = "{notblank}")
	boolean isApproved,

	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long teacherId
) {
}
