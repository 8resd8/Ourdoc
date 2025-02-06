package com.ssafy.ourdoc.domain.bookreport.dto;

import com.ssafy.ourdoc.global.common.enums.OcrCheck;

public record BookReadLogRequest(
	Long bookId,
	String beforeContent,
	String imageUrl,
	OcrCheck ocrCheck
) {
}
