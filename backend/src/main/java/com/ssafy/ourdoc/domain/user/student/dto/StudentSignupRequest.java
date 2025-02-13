package com.ssafy.ourdoc.domain.user.student.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;

/**
 * 학생 회원가입 요청 DTO
 */
public record StudentSignupRequest(
	// === User 엔티티에 필요한 필드 ===
	String name,
	String loginId,
	String password,
	String schoolName,
	Long schoolId,
	Integer grade,
	Integer classNumber,
	Integer studentNumber,
	Date birth,
	Gender gender
) {
}
