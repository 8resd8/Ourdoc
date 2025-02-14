package com.ssafy.ourdoc.domain.book.dto.most;

import lombok.Builder;

@Builder
public record BookMostResponse(
	BookMostDto gradeMost,
	BookMostDto classMost
) {
}
