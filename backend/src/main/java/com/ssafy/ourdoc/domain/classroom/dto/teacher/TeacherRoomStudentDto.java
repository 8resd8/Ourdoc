package com.ssafy.ourdoc.domain.classroom.dto.teacher;

import com.querydsl.core.annotations.QueryProjection;

public record TeacherRoomStudentDto(
	String studentName,
	int studentNumber
) {
	@QueryProjection
	public TeacherRoomStudentDto {
	}
}
