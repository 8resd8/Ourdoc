package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportMyRankDto(
	Long userId,
	int stampCount,
	int rank
) {
}
