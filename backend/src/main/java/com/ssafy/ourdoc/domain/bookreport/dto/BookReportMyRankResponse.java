package com.ssafy.ourdoc.domain.bookreport.dto;

public record BookReportMyRankResponse(
	int lastRank,
	int stampCount,
	int rank
) {
}
