package com.ssafy.ourdoc.domain.book.dto;

import lombok.Builder;

@Builder
public record BookMostDto(
	BookDetailDto book,
	int submitCount
) {
}
