package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.util.List;

import lombok.Builder;

@Builder
public record BookRecommendResponseStudent(
	List<BookRecommendDetailStudent> recommend
) {
}
