package com.ssafy.ourdoc.domain.book.dto;

import java.time.Year;

import lombok.Builder;

@Builder
public record BookDetailDto(
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
}
