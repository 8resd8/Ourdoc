package com.ssafy.ourdoc.domain.book.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;

import lombok.Builder;

@Builder
public record BookRecommendDetailStudent(
	BookResponse book,
	LocalDateTime creatAt,
	boolean submitStatus
) {
	public static BookRecommendDetailStudent of(Book book, BookRecommend bookRecommend, boolean submitStatus) {
		return BookRecommendDetailStudent.builder()
			.book(BookResponse.of(book))
			.creatAt(bookRecommend.getCreatedAt())
			.submitStatus(submitStatus)
			.build();
	}
}
