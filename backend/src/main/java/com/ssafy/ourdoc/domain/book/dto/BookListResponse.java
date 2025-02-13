package com.ssafy.ourdoc.domain.book.dto;

import org.springframework.data.domain.Page;

public record BookListResponse(Page<BookResponse> book) {
}
