package com.ssafy.ourdoc.domain.book.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookRequest(
	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long bookId
) {
}