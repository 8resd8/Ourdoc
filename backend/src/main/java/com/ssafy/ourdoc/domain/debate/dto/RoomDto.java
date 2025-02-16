package com.ssafy.ourdoc.domain.debate.dto;

import java.time.LocalDateTime;

public record RoomDto(
	Long roomId,
	String title,
	String creatorName,
	int maxPeople,
	Long currentPeople,
	String schoolName,
	LocalDateTime createdAt
) {
}
