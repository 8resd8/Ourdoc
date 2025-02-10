package com.ssafy.ourdoc.domain.debate.dto;

import com.ssafy.ourdoc.global.common.enums.UserType;

public record OnlineUserDto(
	String schoolName,
	String name,
	UserType userType,
	String profileImagePath
) {
}
