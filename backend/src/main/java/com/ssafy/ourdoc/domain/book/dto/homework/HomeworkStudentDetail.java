package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record HomeworkStudentDetail(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	boolean submitStatus,
	List<BookReportHomeworkStudent> bookReports
) {
	public static HomeworkStudentDetail of(Homework homework, boolean submitStatus,
		List<BookReportHomeworkStudent> bookReports) {
		return HomeworkStudentDetail.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook()))
			.createdAt(homework.getCreatedAt())
			.submitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
