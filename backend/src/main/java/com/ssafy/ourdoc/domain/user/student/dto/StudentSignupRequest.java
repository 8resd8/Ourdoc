package com.ssafy.ourdoc.domain.user.student.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;

public record StudentSignupRequest(
	// === User 엔티티에 필요한 필드 ===
	String name,
	String loginId,
	String password,
	String schoolName,
	Long schoolId,
	Long classId,
	Integer grade,
	Integer classNumber,
	Integer studentNumber,
	Date birth,
	Gender gender
) {
}
