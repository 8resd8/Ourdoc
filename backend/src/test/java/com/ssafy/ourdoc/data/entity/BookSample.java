package com.ssafy.ourdoc.data.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.entity.Book;

public class BookSample {
	private BookSample() {
	}

	public static Book book(String title) {
		return Book.builder()
			.isbn("테스트번호")
			.title(title)
			.author("테스트지은이")
			.genre("테스트장르")
			.description("테스트설명")
			.publisher("테스트출판사")
			.publishTime(LocalDate.from(LocalDateTime.now()))
			.imageUrl("테스트이미지")
			.build();
	}

	public static Book book() {
		return Book.builder()
			.isbn("테스트번호")
			.title("테스트제목")
			.author("테스트지은이")
			.genre("테스트장르")
			.description("테스트설명")
			.publisher("테스트출판사")
			.publishTime(LocalDate.from(LocalDateTime.now()))
			.imageUrl("테스트이미지")
			.build();
	}
}
