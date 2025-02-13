package com.ssafy.ourdoc.domain.book.dto.homework;

public record HomeworkPageable(
	int bookPage,
	int bookSize,
	int reportPage,
	int reportSize
) {
}
