package com.ssafy.ourdoc.domain.bookreport.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackRequest(
	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Long bookReportId,

	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String feedbackContent, // 피드백 내용

	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String spellingContent // 맞춤법 내용
) {
}
