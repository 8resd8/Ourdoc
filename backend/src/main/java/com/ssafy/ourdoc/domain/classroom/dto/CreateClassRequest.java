package com.ssafy.ourdoc.domain.classroom.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateClassRequest(
	@Size(max = 250, message = "{size.max}")
	String schoolName,

	@Size(max = 250, message = "{size.max}")
	String schoolAddress,

	@Min(value = 1900, message = "{size.min}")
	@Max(value = 2200, message = "{size.max}")
	int year,

	@Min(value = 1, message = "{size.min}")
	@Max(value = 6, message = "{size.max}")
	int grade,

	@Min(value = 1, message = "{size.min}")
	@Max(value = 250, message = "{size.max}")
	int classNumber
) {
}
