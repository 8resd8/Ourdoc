package com.ssafy.ourdoc.domain.award.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AwardTeacherCreateRequest(
	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String title
) {
}
