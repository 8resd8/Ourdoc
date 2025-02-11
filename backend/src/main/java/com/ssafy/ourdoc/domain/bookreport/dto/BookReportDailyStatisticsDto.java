package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportDailyStatisticsDto(
	int day,
	int readCount
) {
}