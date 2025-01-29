package com.ssafy.ourdoc.book.dto;

import lombok.Builder;

@Builder
public record NationalLibraryBookRequest(
	String title,
	String author,
	String publisher
) {
}
