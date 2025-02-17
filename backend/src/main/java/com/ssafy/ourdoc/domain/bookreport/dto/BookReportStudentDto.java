package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record BookReportStudentDto(
	Long bookreportId,
	String beforeContent,
	LocalDateTime createdAt,
	Long homeworkId,
	LocalDateTime approveTime
) {
	@QueryProjection
	public BookReportStudentDto {

	}
}
