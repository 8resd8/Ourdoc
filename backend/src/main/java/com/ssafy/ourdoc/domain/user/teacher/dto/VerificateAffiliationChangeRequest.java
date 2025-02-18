package com.ssafy.ourdoc.domain.user.teacher.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VerificateAffiliationChangeRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String studentLoginId,

	@NotNull(message = "{notblank}")
	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{length.max}")
	Integer studentNumber,

	@NotNull(message = "{notblank}")
	Boolean isApproved
) {
}
