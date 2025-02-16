package com.ssafy.ourdoc.domain.award.dto.teacher;

import java.time.LocalDateTime;

public record AwardTeacherDto(
	Long id,
	String title,
	String imagePath,
	LocalDateTime createdAt
) {
}
