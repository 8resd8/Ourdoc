package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponseWithId;

import lombok.Builder;

@Builder
public record HomeworkDetailTeacher(
	Long bookReportId,
	BookResponse book,
	LocalDateTime createdAt,
	int submitCount,
	List<ReportTeacherResponseWithId> bookReports
) {
}
