package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportStatisticsResponse(
	int myBookReportCount,
	double classAverageBookReportCount,
	int classHighestBookReportCount
) {
}
