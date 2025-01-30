package com.ssafy.ourdoc.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 요청에 대한 응답 DTO
 */
@Getter
@Builder
public class LoginResponse {
    private String resultCode;  // 예: "200", "401"
    private String message;     // 예: "로그인 성공", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다."
    private UserInfo user;      // 로그인 성공 시 사용자 정보 (null 가능)

    @Getter
    @Builder
    public static class UserInfo {
        private String id;      // User.loginId
        private String name;    // User.name
        private String role;    // User.userType (학생, 교사, 관리자)
    }
}
