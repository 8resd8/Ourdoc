package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record ReportTeacherDto(
	String bookTitle,
	int studentNumber,
	String studentName,
	LocalDateTime createdAt,
	LocalDateTime approveTime) {
	
	@QueryProjection
	public ReportTeacherDto {
	}
}
