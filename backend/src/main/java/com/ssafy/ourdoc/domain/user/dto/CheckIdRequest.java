package com.ssafy.ourdoc.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckIdRequest(
	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String loginId
) {
}
