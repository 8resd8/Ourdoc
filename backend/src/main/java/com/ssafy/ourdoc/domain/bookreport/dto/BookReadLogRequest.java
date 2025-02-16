package com.ssafy.ourdoc.domain.bookreport.dto;

import com.ssafy.ourdoc.global.common.enums.OcrCheck;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookReadLogRequest(
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long bookId,

	@Size(max = 250, message = "{size.max}")
	Long homeworkId,

	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String beforeContent,

	@Size(max = 250, message = "{size.max}")
	String imageUrl,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	OcrCheck ocrCheck
) {
}
