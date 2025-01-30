package com.ssafy.ourdoc.user.teacher.dto;

import com.ssafy.ourdoc.global.enums.Active;
import com.ssafy.ourdoc.global.enums.Gender;
import com.ssafy.ourdoc.global.enums.EmploymentStatus;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
public class TeacherSignupRequest {
    // === User 엔티티 관련 필드 ===
    private String name;
    private String loginId;
    private String password;
    private Date birth;
    private Gender gender;
    private Active active; // 예: 활성/비활성

    // === Teacher 엔티티 관련 필드 ===
    private String email;
    private String phone;
//    private EmploymentStatus employmentStatus;
//    private LocalDateTime certificateTime; // 증명서 발급 시간 등
}
