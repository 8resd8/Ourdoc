package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record BookReportTeacherDto(
	Long id,
	String beforeContent,
	int studentNumber,
	String studentName,
	LocalDateTime createdAt,
	LocalDateTime approveTime) {

	@QueryProjection
	public BookReportTeacherDto {
	}
}
