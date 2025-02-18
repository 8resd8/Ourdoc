package com.ssafy.ourdoc.domain.bookreport.dto.student;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.global.common.enums.HomeworkStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record BookReportListDto(
	Long bookId,
	Long bookReportId,
	String bookTitle,
	String bookImagePath,
	Homework homework,
	LocalDateTime createdAt
) {
	@QueryProjection
	public BookReportListDto {
	}
}
