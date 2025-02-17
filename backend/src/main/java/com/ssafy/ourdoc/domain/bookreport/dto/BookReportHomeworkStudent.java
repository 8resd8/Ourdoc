package com.ssafy.ourdoc.domain.bookreport.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.ApproveStatus;
import com.ssafy.ourdoc.global.common.enums.SubmitStatus;

public record BookReportHomeworkStudent(
	Long id,
	String beforeContent,
	LocalDateTime createdAt,
	SubmitStatus homeworkSubmitStatus,
	ApproveStatus bookReportApproveStatus
) {

}
