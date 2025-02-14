package com.ssafy.ourdoc.domain.debate.dto;

import jakarta.validation.constraints.Size;

public record UpdateRoomRequest(
	@Size(max = 250, message = "{size.max}")
	String title,

	@Size(max = 250, message = "{size.max}")
	String password,

	@Size(max = 250, message = "{size.max}")
	Integer maxPeople
) {
}
