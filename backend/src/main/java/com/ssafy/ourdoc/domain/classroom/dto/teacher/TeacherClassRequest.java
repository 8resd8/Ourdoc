package com.ssafy.ourdoc.domain.classroom.dto.teacher;

import jakarta.validation.constraints.Positive;

public record TeacherClassRequest(
	@Positive(message = "{positive}")
	Long classId
) {
}
