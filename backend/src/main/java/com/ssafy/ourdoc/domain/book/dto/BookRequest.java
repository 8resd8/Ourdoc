package com.ssafy.ourdoc.domain.book.dto;

import lombok.Builder;

@Builder
public record BookRequest(
	String title,
	String author,
	String publisher
) {
}
