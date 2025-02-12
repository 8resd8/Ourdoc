package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.ApproveStatus;
import com.ssafy.ourdoc.global.common.enums.HomeworkStatus;

public record BookReportDto(
	Long bookReportId,
	String content,
	LocalDateTime submitTime,
	ApproveStatus approveStatus,
	HomeworkStatus homework
) {
}
