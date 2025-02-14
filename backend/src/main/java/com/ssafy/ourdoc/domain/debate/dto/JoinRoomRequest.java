package com.ssafy.ourdoc.domain.debate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JoinRoomRequest(
	@Size(min = 2, max = 250, message = "{size}")
	@NotBlank(message = "{notblank}")
	String password
) {
}
