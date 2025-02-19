package com.ssafy.ourdoc.global.integration.gpt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SpellingRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String content
) {
}
