package com.ssafy.ourdoc.domain.user.dto;

public record TeacherVerificationDto(
	Long teacherId,
	String loginId,
	String name,
	String email,
	String phone,
	String certificateImageUrl
) {
}
