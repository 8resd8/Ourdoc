package com.ssafy.ourdoc.domain.classroom.dto;

public record CreateClassRequest(
	String schoolName,
	int year,
	int grade,
	int classNumber
) {
}
