package com.ssafy.ourdoc.domain.book.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record BookRecommendResponseTeacher(
	int studentCount,
	List<BookRecommendDetailTeacher> recommend
) {
}
