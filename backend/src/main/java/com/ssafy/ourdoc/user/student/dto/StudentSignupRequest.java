package com.ssafy.ourdoc.user.student.dto;

import com.ssafy.ourdoc.global.enums.Active;
import com.ssafy.ourdoc.global.enums.AuthStatus;
import com.ssafy.ourdoc.global.enums.Gender;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 학생 회원가입 요청 DTO
 */
@Getter
public class StudentSignupRequest {
    // === User 엔티티에 필요한 필드 ===
    private String name;
    private String loginId;
    private String password;
    private Date birth;
    private Gender gender;
    private Active active; // 활성/비활성
}
