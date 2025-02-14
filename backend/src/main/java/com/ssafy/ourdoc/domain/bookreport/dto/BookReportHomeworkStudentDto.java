package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record BookReportHomeworkStudentDto(
	Long bookreportId,
	String beforeContent,
	LocalDateTime createdAt,
	Long homeworkId,
	LocalDateTime approveTime
) {
	@QueryProjection
	public BookReportHomeworkStudentDto {

	}
}
