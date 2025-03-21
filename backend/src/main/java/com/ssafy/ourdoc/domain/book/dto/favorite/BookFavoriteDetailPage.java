package com.ssafy.ourdoc.domain.book.dto.favorite;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStudent;

import lombok.Builder;

@Builder
public record BookFavoriteDetailPage(
	Long bookFavoriteId,
	BookResponse book,
	boolean submitStatus,
	Page<BookReportStudent> bookReports
) {
	public static BookFavoriteDetailPage of(BookFavorite bookFavorite, boolean submitStatus,
		Page<BookReportStudent> bookReports, BookStatus bookStatus) {
		return BookFavoriteDetailPage.builder()
			.bookFavoriteId(bookFavorite.getId())
			.book(BookResponse.of(bookFavorite.getBook(), bookStatus))
			.submitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
