package com.ssafy.ourdoc.domain.bookreport.dto;

import com.ssafy.ourdoc.global.annotation.EnumValid;
import com.ssafy.ourdoc.global.common.enums.OcrCheck;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookReadLogRequest(
	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long bookId,

	@Positive(message = "{positive}")
	Long homeworkId,

	@NotBlank(message = "{notblank}")
	@Size(max = 3000, message = "{size.max}")
	String beforeContent,

	@Size(max = 250, message = "{size.max}")
	String imageUrl,

	@EnumValid(enumClass = OcrCheck.class, message = "{not.enum}")
	OcrCheck ocrCheck
) {
}
