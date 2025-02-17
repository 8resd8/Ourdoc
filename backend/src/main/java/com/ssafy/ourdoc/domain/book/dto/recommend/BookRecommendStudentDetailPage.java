package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record BookRecommendStudentDetailPage(
	BookResponse book,
	LocalDateTime createdAt,
	boolean submitStatus,
	Page<BookReportHomeworkStudent> bookReports
) {
	public static BookRecommendStudentDetailPage of(BookRecommend bookRecommend, boolean submitStatus,
		Page<BookReportHomeworkStudent> bookReports, BookStatus bookStatus) {
		return BookRecommendStudentDetailPage.builder()
			.book(BookResponse.of(bookRecommend.getBook(), bookStatus))
			.createdAt(bookRecommend.getCreatedAt())
			.bookReports(bookReports)
			.submitStatus(submitStatus)
			.build();
	}
}
