package com.ssafy.ourdoc.domain.classroom.dto;

public record CreateClassRequest(
	String schoolName,
	String schoolAddress,
	int year,
	int grade,
	int classNumber
) {
}
