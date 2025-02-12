package com.ssafy.ourdoc.domain.debate.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RoomDetailResponse(
	Long roomId,
	String title,
	String creatorName,
	int maxPeople,
	Long currentPeople,
	LocalDateTime createdAt,
	List<OnlineUserDto> onlineUsers
) {
}
