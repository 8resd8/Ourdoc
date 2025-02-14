package com.ssafy.ourdoc.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String newPassword
) {
}
