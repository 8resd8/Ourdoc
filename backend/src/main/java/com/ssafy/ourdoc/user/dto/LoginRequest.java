package com.ssafy.ourdoc.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String userType;  // "학생", "교사", "관리자"
    private String loginId;   // 로그인 ID
    private String password;  // 비밀번호
}
