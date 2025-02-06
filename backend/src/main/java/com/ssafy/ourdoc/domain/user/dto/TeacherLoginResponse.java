package com.ssafy.ourdoc.domain.user.dto;

public record TeacherLoginResponse(
	String resultCode,  // 예: "200", "401"
	String message,     // 예: "로그인 성공", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다."
	String loginId,
	String name,
	String role,
	String schoolName,
	String grade,
	String classNumber
) {}
