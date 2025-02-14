package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;

import lombok.Builder;

@Builder
public record BookRecommendStudentDetail(
	BookResponse book,
	LocalDateTime createdAt,
	boolean submitStatus
) {
	public static BookRecommendStudentDetail of(BookRecommend bookRecommend, boolean submitStatus) {
		return BookRecommendStudentDetail.builder()
			.book(BookResponse.of(bookRecommend.getBook()))
			.createdAt(bookRecommend.getCreatedAt())
			.submitStatus(submitStatus)
			.build();
	}
}
