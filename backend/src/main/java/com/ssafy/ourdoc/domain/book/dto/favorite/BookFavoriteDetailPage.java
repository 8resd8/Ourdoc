package com.ssafy.ourdoc.domain.book.dto.favorite;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record BookFavoriteDetailPage(
	Long bookFavoriteId,
	BookResponse book,
	boolean submitStatus,
	Page<BookReportHomeworkStudent> bookReports
) {
	public static BookFavoriteDetailPage of(BookFavorite bookFavorite,  boolean submitStatus,
		Page<BookReportHomeworkStudent> bookReports, BookStatus bookStatus) {
		return BookFavoriteDetailPage.builder()
			.bookFavoriteId(bookFavorite.getId())
			.book(BookResponse.of(bookFavorite.getBook(), bookStatus))
			.submitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
