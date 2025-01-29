package com.ssafy.ourdoc.user.teacher.service.interf;

import com.ssafy.ourdoc.user.teacher.dto.TeacherSignupRequest;

public interface TeacherSignupService {
    /**
     * 교사 회원가입 기능
     * @param request 교사 회원가입용 DTO
     * @return 가입된 teacher_id (PK)
     * @throws IllegalArgumentException 가입 불가능(중복 ID 등) 시 예외
     */
    Long signup(TeacherSignupRequest request);
}
