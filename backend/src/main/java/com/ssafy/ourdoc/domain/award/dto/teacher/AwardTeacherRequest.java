package com.ssafy.ourdoc.domain.award.dto.teacher;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AwardTeacherRequest(
	@Size(max = 250, message = "{size.max}") // 선택
	String studentLoginId,

	@NotNull(message = "{notblank}")
	@Positive(message = "{positive}")
	Long classId
) {
}
