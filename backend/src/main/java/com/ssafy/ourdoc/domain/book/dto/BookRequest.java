package com.ssafy.ourdoc.domain.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long bookId
) {
}
