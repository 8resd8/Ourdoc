package com.ssafy.ourdoc.domain.book.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record BookDetailDto(
	Long bookId,
	String title,
	String author,
	String genre,
	String description,
	String publisher,
	LocalDate publishTime,
	String imageUrl
) {
}
