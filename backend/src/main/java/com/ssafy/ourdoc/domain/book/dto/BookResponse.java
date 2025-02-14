package com.ssafy.ourdoc.domain.book.dto;

import java.time.Year;

import com.ssafy.ourdoc.domain.book.entity.Book;

import lombok.Builder;

@Builder
public record BookResponse(
	Long bookId,
	String title,
	String author,
	String genre,
	String publisher,
	Year publishYear,
	String imageUrl
) {
	public static BookResponse of(Book book) {
		return BookResponse.builder()
			.bookId(book.getId())
			.title(book.getTitle())
			.author(book.getAuthor())
			.genre(book.getGenre())
			.publisher(book.getPublisher())
			.publishYear(book.getPublishYear())
			.imageUrl(book.getImageUrl())
			.build();
	}
}
