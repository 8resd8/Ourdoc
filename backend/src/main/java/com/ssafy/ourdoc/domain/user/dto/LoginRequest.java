package com.ssafy.ourdoc.domain.user.dto;

import com.ssafy.ourdoc.global.common.enums.UserType;

public record LoginRequest(
	UserType userType,  // "학생", "교사", "관리자"
	String loginId,   // 로그인 ID
	String password  // 비밀번호
) {
}
