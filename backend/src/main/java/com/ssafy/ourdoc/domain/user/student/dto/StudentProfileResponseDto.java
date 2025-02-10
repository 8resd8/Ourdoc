package com.ssafy.ourdoc.domain.user.student.dto;

public record StudentProfileResponseDto(
	String profileImage,
	String name,
	String loginId,
	String schoolName,
	int grade,
	int classNumber,
	int studentNumber
) {
}
