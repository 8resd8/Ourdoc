package com.ssafy.ourdoc.data.entity;

import java.time.LocalDate;

import com.ssafy.ourdoc.domain.book.entity.Book;

public class BookSample {
	private BookSample() {

	}

	private static final String defaultIsbn = "9788925414874";
	private static final String defaultTitle = "학생들이 즐거운 수학교실";
	private static final String defaultAuthor = "김진호";
	private static final String defaultGenre = "사회과학";
	private static final String defaultDescription = "재밌는 수학";
	private static final String defaultPublisher = "교육과학사";
	private static final LocalDate defaultPublishTime = LocalDate.of(2020, 5, 13);
	private static final String defaultImageUrl = "https://www.nl.go.kr/seoji/fu/ecip/dbfiles/CIP_FILES_TBL/2020/04/28/9788925414874.jpg";

	public static Book book() {
		return Book.builder()
			.isbn(defaultIsbn)
			.title(defaultTitle)
			.author(defaultAuthor)
			.genre(defaultGenre)
			.publisher(defaultPublisher)
			.publishTime(defaultPublishTime)
			.imageUrl(defaultImageUrl)
			.build();
	}

	public static Book book(String isbn, String title, String author) {
		return Book.builder()
			.isbn(isbn)
			.title(title)
			.author(author)
			.genre(defaultGenre)
			.description(defaultDescription)
			.publisher(defaultPublisher)
			.publishTime(defaultPublishTime)
			.imageUrl(defaultImageUrl)
			.build();
	}
}
