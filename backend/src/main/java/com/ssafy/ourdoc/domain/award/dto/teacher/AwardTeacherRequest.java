package com.ssafy.ourdoc.domain.award.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AwardTeacherRequest(
	@Size(max = 250, message = "{size.max}") // 선택
	String studentLoginId,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	Long classId
) {
}
