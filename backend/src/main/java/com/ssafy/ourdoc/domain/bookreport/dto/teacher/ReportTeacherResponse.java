package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.ApproveStatus;

// approveTime -> approveStatus 변환 레코드
public record ReportTeacherResponse(
	Long bookReportId,
	String bookTitle,
	int studentNumber,
	String studentName,
	LocalDateTime createdAt,
	ApproveStatus bookReportApproveStatus
) {
}
