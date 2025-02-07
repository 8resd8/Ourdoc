package com.ssafy.ourdoc.domain.user.teacher.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;

public record TeacherSignupRequest(
	// === User 엔티티 관련 필드 ===
	String name,
	String loginId,
	String password,
	Date birth,
	Gender gender,

	// === Teacher 엔티티 관련 필드 ===
	String email,
	String phone
) {
}
