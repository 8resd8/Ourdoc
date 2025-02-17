package com.ssafy.ourdoc.domain.bookreport.dto;

import jakarta.validation.constraints.Max;

public record BookReportStatisticsRequest(
	@Max(value = 250, message = "{length.max}")
	Integer grade,

	@Max(value = 250, message = "{length.max}")
	Integer month
) {
}
