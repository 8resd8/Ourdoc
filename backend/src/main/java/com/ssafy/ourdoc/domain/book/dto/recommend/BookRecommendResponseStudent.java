package com.ssafy.ourdoc.domain.book.dto.recommend;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record BookRecommendResponseStudent(
	Page<BookRecommendDetailStudent> recommends
) {
}
