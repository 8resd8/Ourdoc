package com.ssafy.ourdoc.domain.book.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;

import lombok.Builder;

@Builder
public record BookRecommendDetailTeacher(
	BookResponse book,
	LocalDateTime creatAt,
	int submitCount
) {
	public static BookRecommendDetailTeacher of(Book book, BookRecommend bookRecommend, int submitCount) {
		return BookRecommendDetailTeacher.builder()
			.book(BookResponse.of(book))
			.creatAt(bookRecommend.getCreatedAt())
			.submitCount(submitCount)
			.build();
	}
}
