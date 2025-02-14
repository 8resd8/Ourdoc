package com.ssafy.ourdoc.global.integration.read.dto;

import java.time.Year;

import com.ssafy.ourdoc.domain.book.entity.Book;

public record ReadBookResponse(
	String isbn,
	String title,
	String author,
	String genre,
	String description,
	String publisher,
	Year publishYear,
	String imageUrl
) {
	public static Book toBookEntity(ReadBookResponse response) {
		return Book.builder()
			.isbn(response.isbn())
			.title(response.title())
			.author(response.author())
			.genre(response.genre())
			.description(response.description())
			.publisher(response.publisher())
			.publishYear(response.publishYear() != null ? response.publishYear() : null)
			.imageUrl(response.imageUrl() != null ? response.imageUrl() : null)
			.build();
	}
}
