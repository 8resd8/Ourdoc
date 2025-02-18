package com.ssafy.ourdoc.domain.classroom.dto.teacher;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TeacherClassRequest(
	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long classId
) {
}
