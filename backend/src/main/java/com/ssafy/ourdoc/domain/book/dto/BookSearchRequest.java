package com.ssafy.ourdoc.domain.book.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record BookSearchRequest(
	@Size(max = 250, message = "{size.max}")
	String title,

	@Size(max = 250, message = "{size.max}")
	String author,

	@Size(max = 250, message = "{size.max}")
	String publisher) {
}
