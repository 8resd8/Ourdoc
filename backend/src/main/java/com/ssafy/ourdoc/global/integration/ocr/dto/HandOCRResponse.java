package com.ssafy.ourdoc.global.integration.ocr.dto;

import lombok.Builder;

@Builder
public record HandOCRResponse(
	String originContent,
	String enhancerContent,
	String ocrImagePath
) {
}
