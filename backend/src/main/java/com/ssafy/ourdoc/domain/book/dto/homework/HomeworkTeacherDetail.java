package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.BookReportTeacher;

import lombok.Builder;

@Builder
public record HomeworkTeacherDetail(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	int homeworkSubmitCount,
	List<BookReportTeacher> bookReports
) {
	public static HomeworkTeacherDetail of(Homework homework, int submitCount,
		List<BookReportTeacher> bookReports, BookStatus bookStatus) {
		return HomeworkTeacherDetail.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook(), bookStatus))
			.createdAt(homework.getCreatedAt())
			.homeworkSubmitCount(submitCount)
			.bookReports(bookReports)
			.build();
	}
}
