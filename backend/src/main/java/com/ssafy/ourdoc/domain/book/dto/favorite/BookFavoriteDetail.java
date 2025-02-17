package com.ssafy.ourdoc.domain.book.dto.favorite;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record BookFavoriteDetail(
	Long bookFavoriteId,
	BookResponse book,
	boolean submitStatus,
	List<BookReportHomeworkStudent> bookReports
) {
	public static BookFavoriteDetail of(BookFavorite bookFavorite,  boolean submitStatus,
		List<BookReportHomeworkStudent> bookReports, BookStatus bookStatus) {
		return BookFavoriteDetail.builder()
			.bookFavoriteId(bookFavorite.getId())
			.book(BookResponse.of(bookFavorite.getBook(), bookStatus))
			.submitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
