package com.ssafy.ourdoc.domain.debate.dto;

import com.ssafy.ourdoc.global.common.enums.UserType;

public record OnlineUserDto(
	Long userId,
	String name,
	UserType userType,
	String profileImagePath
) {
}
