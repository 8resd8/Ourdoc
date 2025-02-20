package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import jakarta.persistence.criteria.CriteriaBuilder;

public record BookReportDetailDto(
	Long bookId,
	String bookTitle,
	String author,
	String publisher,
	LocalDateTime createdAt,
	String beforeContent,
	String afterContent,
	String aiComment,
	String teacherComment,
	LocalDateTime approveTime,
	String schoolName,
	Integer grade,
	Integer classNumber,
	Integer studentNumber
) {
	@QueryProjection
	public BookReportDetailDto {
	}
}
