package com.ssafy.ourdoc.user.student.service.interf;

import com.ssafy.ourdoc.user.student.dto.StudentSignupRequest;

public interface StudentSignupService {
    /**
     * 학생 회원가입 기능
     * @param request 학생 회원가입용 DTO
     * @return 가입된 student_id (PK)
     */
    Long signup(StudentSignupRequest request);
}
