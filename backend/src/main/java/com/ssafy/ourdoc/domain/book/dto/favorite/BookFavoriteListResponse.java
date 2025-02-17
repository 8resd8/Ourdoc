package com.ssafy.ourdoc.domain.book.dto.favorite;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record BookFavoriteListResponse(Page<BookFavoriteDetail> favorite) {

}
