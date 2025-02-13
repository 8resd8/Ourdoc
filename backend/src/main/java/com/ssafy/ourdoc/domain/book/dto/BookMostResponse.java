package com.ssafy.ourdoc.domain.book.dto;

import lombok.Builder;

@Builder
public record BookMostResponse(
	BookMostDto gradeMost,
	BookMostDto classMost
) {
}
