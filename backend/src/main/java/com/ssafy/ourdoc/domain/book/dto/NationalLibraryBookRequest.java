package com.ssafy.ourdoc.domain.book.dto;

import lombok.Builder;

@Builder
public record NationalLibraryBookRequest(
	String title,
	String author,
	String publisher
) {
}
