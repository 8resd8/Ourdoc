package com.ssafy.ourdoc.domain.book.dto;

import java.time.LocalDate;

import com.ssafy.ourdoc.domain.book.entity.Book;

import lombok.Builder;

@Builder
public record BookResponse(
	Long id,
	String title,
	String author,
	String genre,
	String publisher,
	LocalDate publishTime,
	String imageUrl
) {
	public static BookResponse of(Book book) {
		return BookResponse.builder()
			.id(book.getId())
			.title(book.getTitle())
			.author(book.getAuthor())
			.genre(book.getGenre())
			.publisher(book.getPublisher())
			.publishTime(book.getPublishTime())
			.imageUrl(book.getImageUrl())
			.build();
	}
}
