package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ourdoc.global.common.enums.ApproveStatus;
import com.ssafy.ourdoc.global.common.enums.SubmitStatus;

public record BookReportHomeworkStudent(
	Long id,
	LocalDateTime createdAt,
	SubmitStatus submitStatus,
	ApproveStatus approveStatus
) {
	@QueryProjection
	public BookReportHomeworkStudent {

	}
}
