package com.ssafy.ourdoc.domain.book.dto.recommend;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record BookRecommendStudentDetail(
	Long bookRecommendId,
	BookResponse book,
	LocalDateTime createdAt,
	boolean submitStatus,
	List<BookReportHomeworkStudent> bookReports
) {
	public static BookRecommendStudentDetail of(BookRecommend bookRecommend, boolean submitStatus,
		List<BookReportHomeworkStudent> bookReports, BookStatus bookStatus) {
		return BookRecommendStudentDetail.builder()
			.bookRecommendId(bookRecommend.getId())
			.book(BookResponse.of(bookRecommend.getBook(), bookStatus))
			.createdAt(bookRecommend.getCreatedAt())
			.bookReports(bookReports)
			.submitStatus(submitStatus)
			.build();
	}
}
