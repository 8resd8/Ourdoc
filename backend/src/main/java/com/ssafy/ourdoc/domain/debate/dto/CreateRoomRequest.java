package com.ssafy.ourdoc.domain.debate.dto;

public record CreateRoomRequest(
	String title,
	String password,
	int maxPeople
	) {
}
