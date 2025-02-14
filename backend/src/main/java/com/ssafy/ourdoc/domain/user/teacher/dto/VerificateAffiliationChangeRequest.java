package com.ssafy.ourdoc.domain.user.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerificateAffiliationChangeRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	String studentLoginId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Integer studentNumber,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	boolean isApproved
) {
}
