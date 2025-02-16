package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;

import lombok.Builder;

@Builder
public record BookRecommendTeacherDetail(
	BookResponse book,
	LocalDateTime createdAt,
	int submitCount
) {
	public static BookRecommendTeacherDetail of(BookRecommend bookRecommend, int submitCount, BookStatus bookStatus) {
		return BookRecommendTeacherDetail.builder()
			.book(BookResponse.of(bookRecommend.getBook(), bookStatus))
			.createdAt(bookRecommend.getCreatedAt())
			.submitCount(submitCount)
			.build();
	}
}
