package com.ssafy.ourdoc.domain.bookreport.dto;

import java.util.List;

public record BookReportRankResponse(
	List<BookReportRankDto> rankList,
	int totalReadCount
) {
}
