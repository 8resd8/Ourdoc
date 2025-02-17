package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.ApproveStatus;

import lombok.Builder;

// 승인여부 변환 레코드
@Builder
public record BookReportDetailResponse(
	String bookTitle,
	String author,
	String publisher,
	LocalDateTime createdAt,
	String beforeContent,
	String afterContent,
	String aiComment,
	String teacherComment,
	ApproveStatus bookReportApproveStatus
) {
}
