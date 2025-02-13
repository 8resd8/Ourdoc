package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponseWithId;

import lombok.Builder;

@Builder
public record HomeworkDetailTeacher(
	Long homeworkId,
	BookResponse book,
	LocalDateTime createdAt,
	int submitCount,
	List<ReportTeacherResponseWithId> bookReports
) {
	public static HomeworkDetailTeacher of(Homework homework, int submitCount,
		List<ReportTeacherResponseWithId> bookReports) {
		return HomeworkDetailTeacher.builder()
			.homeworkId(homework.getId())
			.book(BookResponse.of(homework.getBook()))
			.createdAt(homework.getCreatedAt())
			.submitCount(submitCount)
			.bookReports(bookReports)
			.build();
	}
}
