package com.ssafy.ourdoc.global.integration.gpt.dto;

import jakarta.validation.constraints.NotBlank;

public record FeedbackRequest(
	@NotBlank(message = "{notblank}")
	String content
) {
}
