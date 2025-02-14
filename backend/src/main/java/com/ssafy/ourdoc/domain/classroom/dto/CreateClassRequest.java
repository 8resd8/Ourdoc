package com.ssafy.ourdoc.domain.classroom.dto;

import jakarta.validation.constraints.Size;

public record CreateClassRequest(
	@Size(max = 250, message = "{size.max}")
	String schoolName,

	@Size(max = 250, message = "{size.max}")
	String schoolAddress,

	@Size(max = 250, message = "{size.max}")
	int year,

	@Size(max = 250, message = "{size.max}")
	int grade,

	@Size(max = 250, message = "{size.max}")
	int classNumber
) {
}
