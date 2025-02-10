package com.ssafy.ourdoc.domain.user.dto.request;

public record TeacherVerificationRequest(
	boolean isApproved,
	Long teacherId
) {
}
