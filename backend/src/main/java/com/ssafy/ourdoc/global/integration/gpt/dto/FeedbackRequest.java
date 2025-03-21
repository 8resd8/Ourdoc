package com.ssafy.ourdoc.global.integration.gpt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FeedbackRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String content,

	@NotBlank(message = "{notblank}")
	@Size(max = 300, message = "{size.max}")
	String bookTitle
) {
}
