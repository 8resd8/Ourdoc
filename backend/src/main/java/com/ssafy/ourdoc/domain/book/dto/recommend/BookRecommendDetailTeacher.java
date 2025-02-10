package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;

import lombok.Builder;

@Builder
public record BookRecommendDetailTeacher(
	BookResponse book,
	LocalDateTime createdAt,
	int submitCount
) {
	public static BookRecommendDetailTeacher of(BookRecommend bookRecommend, int submitCount) {
		return BookRecommendDetailTeacher.builder()
			.book(BookResponse.of(bookRecommend.getBook()))
			.createdAt(bookRecommend.getCreatedAt())
			.submitCount(submitCount)
			.build();
	}
}
