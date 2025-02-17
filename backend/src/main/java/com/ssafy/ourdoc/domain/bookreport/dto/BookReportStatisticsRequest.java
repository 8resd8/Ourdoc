package com.ssafy.ourdoc.domain.bookreport.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record BookReportStatisticsRequest(
	@Min(value = 1, message = "{size.min}")
	@Max(value = 20, message = "{size.max}")
	Integer grade,

	@Min(value = 1, message = "{size.min}")
	@Max(value = 12, message = "{size.max}")
	Integer month
) {
}
