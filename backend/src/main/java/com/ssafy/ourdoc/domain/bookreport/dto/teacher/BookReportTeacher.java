package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.ApproveStatus;

public record BookReportTeacher(
	Long bookreportId,
	int studentNumber,
	String studentName,
	LocalDateTime createdAt,
	ApproveStatus bookReportApproveStatus
) {
}
