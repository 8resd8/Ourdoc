package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportStatisticsResponse(
	int readCount,
	double averageReadCount,
	int bestReadCount
) {
}
