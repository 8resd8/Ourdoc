package com.ssafy.ourdoc.domain.bookreport.dto.student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record BookReportListRequest(
	@Min(value = 1, message = "{size.min}")
	@Max(value = 6, message = "{size.max}")
	int grade,

	@Positive(message = "{positive}")
	Long classId
) {
}
