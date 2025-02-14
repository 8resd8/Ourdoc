package com.ssafy.ourdoc.domain.book.dto.most;

import com.ssafy.ourdoc.domain.book.dto.BookDetailDto;

import lombok.Builder;

@Builder
public record BookMostDto(
	BookDetailDto book,
	int submitCount
) {
}
