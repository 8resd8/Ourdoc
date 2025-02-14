package com.ssafy.ourdoc.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeacherVerificationRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	boolean isApproved,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long teacherId
) {
}
