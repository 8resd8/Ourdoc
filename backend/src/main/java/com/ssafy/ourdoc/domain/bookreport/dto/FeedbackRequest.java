package com.ssafy.ourdoc.domain.bookreport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FeedbackRequest(
	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long bookReportId,

	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String feedbackContent, // 피드백 내용

	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String spellingContent // 맞춤법 내용
) {
}
