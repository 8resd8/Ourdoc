package com.ssafy.ourdoc.global.integration.nationallibrary.dto;

import lombok.Builder;

@Builder
public record NationalLibraryBookRequest(
	String title,
	String author,
	String publisher
) {
}
