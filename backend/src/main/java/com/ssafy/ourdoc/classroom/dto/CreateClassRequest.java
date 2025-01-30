package com.ssafy.ourdoc.classroom.dto;

public record CreateClassRequest(
	String schoolName,
	int year,
	int grade,
	int classNumber
) {
}
