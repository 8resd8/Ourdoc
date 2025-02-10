package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ourdoc.global.common.enums.ApproveStatus;

public record BookReportHomeworkStudent(
	Long id,
	LocalDateTime createdAt,
	boolean submitStatus,
	ApproveStatus approveStatus
) {
	@QueryProjection
	public BookReportHomeworkStudent {

	}
}
