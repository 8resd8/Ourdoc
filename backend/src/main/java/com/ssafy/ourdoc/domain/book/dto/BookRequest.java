package com.ssafy.ourdoc.domain.book.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Long bookId
) {
}
