package com.ssafy.ourdoc.domain.book.dto;

import java.time.Year;

import com.ssafy.ourdoc.domain.book.entity.Book;

import lombok.Builder;

@Builder
public record BookDetailResponse(
	Long bookId,
	String title,
	String author,
	String genre,
	String description,
	String publisher,
	Year publishYear,
	String imageUrl,
	BookStatus bookStatus
) {
	public static BookDetailResponse of(Book book, String description, BookStatus bookStatus) {
		return BookDetailResponse.builder()
			.bookId(book.getId())
			.title(book.getTitle())
			.author(book.getAuthor())
			.genre(book.getGenre())
			.publisher(book.getPublisher())
			.publishYear(book.getPublishYear())
			.imageUrl(book.getImageUrl())
			.description(description)
			.bookStatus(bookStatus)
			.build();
	}
}
