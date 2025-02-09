package com.ssafy.ourdoc.domain.user.teacher.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.ssafy.ourdoc.global.common.enums.Gender;

public record StudentProfileDto(
	String name,
	String loginId,
	Date birth,
	Gender gender,
	int studentNumber,
	LocalDateTime certificateTime
) {
}
