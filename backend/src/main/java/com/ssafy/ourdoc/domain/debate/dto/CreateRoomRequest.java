package com.ssafy.ourdoc.domain.debate.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRoomRequest(
	@Size(max = 250, message = "{size.max}")
	String title,

	@Size(max = 250, message = "{size.max}")
	String password,

	@NotNull(message = "{notblank}")
	@Max(value = 250, message = "{length.max}")
	Integer maxPeople
) {
}
