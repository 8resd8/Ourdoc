package com.ssafy.ourdoc.domain.book.dto;

import lombok.Builder;

@Builder
public record BookSearchRequest(
	String title,
	String author,
	String publisher
) {
}
