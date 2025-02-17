package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportMyRankDto(
	Long userId,
	int readCount,
	int rank
) {
}
