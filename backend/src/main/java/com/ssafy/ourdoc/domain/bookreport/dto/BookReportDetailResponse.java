package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.ApproveStatus;

// 승인여부 변환 레코드
public record BookReportDetailResponse(
	String bookTitle,
	String author,
	String publisher,
	LocalDateTime createdAt,
	String content,
	String aiComment,
	String teacherComment,
	ApproveStatus approveStatus
) {
}
