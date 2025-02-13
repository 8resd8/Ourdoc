package com.ssafy.ourdoc.domain.debate.dto;

public record RoomDto(
	Long roomId,
	String title,
	String creatorName,
	int maxPeople,
	Long currentPeople,
	String schoolName
) {
}
