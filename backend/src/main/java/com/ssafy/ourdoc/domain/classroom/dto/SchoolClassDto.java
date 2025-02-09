package com.ssafy.ourdoc.domain.classroom.dto;

import java.time.Year;

import com.querydsl.core.annotations.QueryProjection;

public record SchoolClassDto(
	Long id,
	String schoolName,
	int grade,
	int classNumber,
	Year year,
	int studentCount
) {
	@QueryProjection
	public SchoolClassDto {
	}
}
