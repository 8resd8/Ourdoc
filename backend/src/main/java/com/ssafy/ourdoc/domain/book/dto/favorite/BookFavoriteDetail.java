package com.ssafy.ourdoc.domain.book.dto.favorite;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;

import lombok.Builder;

@Builder
public record BookFavoriteDetail(
	BookResponse book,
	boolean submitStatus
) {
	public static BookFavoriteDetail of(BookFavorite bookFavorite, boolean submitStatus, BookStatus bookStatus) {
		return BookFavoriteDetail.builder()
			.book(BookResponse.of(bookFavorite.getBook(), bookStatus))
			.submitStatus(submitStatus)
			.build();
	}
}
