package com.ssafy.ourdoc.domain.user.dto;

/**
 * 로그인 요청에 대한 응답 DTO (record 사용)
 */
public record LoginResponse(
	String resultCode,  // 예: "200", "401"
	String message,     // 예: "로그인 성공", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다."
	String token,
	UserInfo user       // 로그인 성공 시 사용자 정보 (null 가능)
) {
	public static LoginResponse of(String resultCode, String message, String token, UserInfo user) {
		return new LoginResponse(resultCode, message, token, user);
	}

	public record UserInfo(
		String id,    // User.loginId
		String name,  // User.name
		String role   // User.userType (학생, 교사, 관리자)
	) {
		public static UserInfo of(String id, String name, String role) {
			return new UserInfo(id, name, role);
		}
	}
}
