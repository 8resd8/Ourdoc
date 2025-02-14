package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponseWithId;

import lombok.Builder;

@Builder
public record HomeworkTeacherDetailPage(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	int submitCount,
	Page<ReportTeacherResponseWithId> bookReports
) {
	public static HomeworkTeacherDetailPage of(Homework homework, int submitCount,
		Page<ReportTeacherResponseWithId> bookReports) {
		return HomeworkTeacherDetailPage.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook()))
			.createdAt(homework.getCreatedAt())
			.submitCount(submitCount)
			.bookReports(bookReports)
			.build();
	}
}
