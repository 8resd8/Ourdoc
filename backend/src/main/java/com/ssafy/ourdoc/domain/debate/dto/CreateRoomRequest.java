package com.ssafy.ourdoc.domain.debate.dto;

import jakarta.validation.constraints.Size;

public record CreateRoomRequest(
	@Size(min = 2, max = 250, message = "{size}")
	String title,

	@Size(min = 2, max = 250, message = "{size}")
	String password
) {
}
