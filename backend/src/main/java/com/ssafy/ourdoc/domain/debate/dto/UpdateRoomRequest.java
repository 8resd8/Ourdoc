package com.ssafy.ourdoc.domain.debate.dto;

public record UpdateRoomRequest(
	String title,
	String password,
	Integer maxPeople
	) {
}