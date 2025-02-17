package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.BookReportTeacher;

import lombok.Builder;

@Builder
public record HomeworkTeacherDetailPage(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	int homeworkSubmitCount,
	Page<BookReportTeacher> bookReports
) {
	public static HomeworkTeacherDetailPage of(Homework homework, int submitCount,
		Page<BookReportTeacher> bookReports, BookStatus bookStatus) {
		return HomeworkTeacherDetailPage.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook(), bookStatus))
			.createdAt(homework.getCreatedAt())
			.homeworkSubmitCount(submitCount)
			.bookReports(bookReports)
			.build();
	}
}
