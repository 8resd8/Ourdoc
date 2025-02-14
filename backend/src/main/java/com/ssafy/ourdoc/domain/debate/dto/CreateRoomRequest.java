package com.ssafy.ourdoc.domain.debate.dto;

import jakarta.validation.constraints.Size;

public record CreateRoomRequest(
	@Size(max = 250, message = "{size.max}")
	String title,

	@Size(max = 250, message = "{size.max}")
	String password,

	@Size(max = 250, message = "{size.max}")
	int maxPeople
) {
}
