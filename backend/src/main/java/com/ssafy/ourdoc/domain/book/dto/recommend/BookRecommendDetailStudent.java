package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;

import lombok.Builder;

@Builder
public record BookRecommendDetailStudent(
	BookResponse book,
	LocalDateTime createdAt,
	boolean submitStatus
) {
	public static BookRecommendDetailStudent of(BookRecommend bookRecommend, boolean submitStatus) {
		return BookRecommendDetailStudent.builder()
			.book(BookResponse.of(bookRecommend.getBook()))
			.createdAt(bookRecommend.getCreatedAt())
			.submitStatus(submitStatus)
			.build();
	}
}
