package com.ssafy.ourdoc.domain.book.dto;

import lombok.Builder;

@Builder
public record BookStatus(
	boolean favorite,
	boolean recommend,
	boolean homework
) {
}
