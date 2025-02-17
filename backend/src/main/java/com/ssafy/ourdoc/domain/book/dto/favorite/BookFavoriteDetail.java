package com.ssafy.ourdoc.domain.book.dto.favorite;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;

import lombok.Builder;

@Builder
public record BookFavoriteDetail(
	BookResponse book
) {
	public static BookFavoriteDetail of(BookFavorite bookFavorite, BookStatus bookStatus) {
		return BookFavoriteDetail.builder()
			.book(BookResponse.of(bookFavorite.getBook(), bookStatus))
			.build();
	}
}
