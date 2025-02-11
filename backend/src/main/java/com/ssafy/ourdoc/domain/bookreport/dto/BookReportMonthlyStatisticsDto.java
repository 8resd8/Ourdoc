package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportMonthlyStatisticsDto(
	int month,
	int readCount
) {
}
