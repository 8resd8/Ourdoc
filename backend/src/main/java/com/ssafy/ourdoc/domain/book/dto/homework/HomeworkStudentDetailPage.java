package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record HomeworkStudentDetailPage(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	boolean submitStatus,
	Page<BookReportHomeworkStudent> bookReports
) {
	public static HomeworkStudentDetailPage of(Homework homework, boolean submitStatus,
		Page<BookReportHomeworkStudent> bookReports, BookStatus bookStatus) {
		return HomeworkStudentDetailPage.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook(), bookStatus))
			.createdAt(homework.getCreatedAt())
			.submitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
