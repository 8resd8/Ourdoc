package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record BookReportDetailDto(
	String bookTitle,
	String author,
	String publisher,
	LocalDateTime createdAt,
	String beforeContent,
	String afterContent,
	String aiComment,
	String teacherComment,
	LocalDateTime approveTime
) {
	@QueryProjection
	public BookReportDetailDto {
	}
}
