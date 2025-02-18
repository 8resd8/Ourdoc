package com.ssafy.ourdoc.domain.bookreport.dto.student;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.global.common.enums.HomeworkStatus;

public record BookReportListDtoConvert(
	Long bookId,
	Long bookReportId,
	String bookTitle,
	String bookImagePath,
	boolean isHomework,
	LocalDateTime createdAt
) {
	@QueryProjection
	public BookReportListDtoConvert {
	}
}
