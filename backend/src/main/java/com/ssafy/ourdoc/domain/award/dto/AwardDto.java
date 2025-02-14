package com.ssafy.ourdoc.domain.award.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AwardDto(
	Long id,
	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String imagePath,

	@NotBlank(message = "{notblank}")
	@Size(min = 2, max = 250, message = "{size}")
	String title,
	LocalDateTime createdAt) {
}
