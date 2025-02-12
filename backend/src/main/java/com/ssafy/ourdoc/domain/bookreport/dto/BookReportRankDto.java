package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportRankDto(
	int studentNumber,
	String name,
	int readCount,
	int rank
) {
}
