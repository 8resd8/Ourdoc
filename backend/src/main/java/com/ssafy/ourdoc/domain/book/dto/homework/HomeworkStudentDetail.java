package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record HomeworkStudentDetail(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	boolean homeworkSubmitStatus,
	List<BookReportHomeworkStudent> bookReports
) {
	public static HomeworkStudentDetail of(Homework homework, boolean submitStatus,
		List<BookReportHomeworkStudent> bookReports, BookStatus bookStatus) {
		return HomeworkStudentDetail.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook(), bookStatus))
			.createdAt(homework.getCreatedAt())
			.homeworkSubmitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
