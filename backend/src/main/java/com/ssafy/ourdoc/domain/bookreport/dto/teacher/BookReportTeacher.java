package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record BookReportTeacher(
	Long id,
	int studentNumber,
	String studentName,
	LocalDateTime createdAt,
	LocalDateTime approveTime) {

	@QueryProjection
	public BookReportTeacher {
	}
}
