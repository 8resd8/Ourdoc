package com.ssafy.ourdoc.domain.book.dto.favorite;

import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStudent;

import lombok.Builder;

@Builder
public record BookFavoriteDetail(
	BookResponse book,
	boolean submitStatus,
	List<BookReportStudent> bookReports
) {
	public static BookFavoriteDetail of(BookFavorite bookFavorite, boolean submitStatus,
		List<BookReportStudent> bookReports, BookStatus bookStatus) {
		return BookFavoriteDetail.builder()
			.book(BookResponse.of(bookFavorite.getBook(), bookStatus))
			.submitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
