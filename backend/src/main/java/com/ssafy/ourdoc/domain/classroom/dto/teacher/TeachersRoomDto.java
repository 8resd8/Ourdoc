package com.ssafy.ourdoc.domain.classroom.dto.teacher;

import java.time.Year;

import com.querydsl.core.annotations.QueryProjection;

public record TeachersRoomDto(
	String schoolName,
	Year year,
	int grade,
	int classNumber
) {
	@QueryProjection
	public TeachersRoomDto {
	}
}
