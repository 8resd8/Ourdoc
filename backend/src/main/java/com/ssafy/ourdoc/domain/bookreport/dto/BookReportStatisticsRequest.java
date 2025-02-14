package com.ssafy.ourdoc.domain.bookreport.dto;

import jakarta.validation.constraints.Size;

public record BookReportStatisticsRequest(
	@Size(max = 250, message = "{size.max}")
	int grade,

	@Size(max = 250, message = "{size.max}")
	int month
) {
}
