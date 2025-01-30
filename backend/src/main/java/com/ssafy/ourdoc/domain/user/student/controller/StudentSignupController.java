package com.ssafy.ourdoc.domain.user.student.controller;

import com.ssafy.ourdoc.domain.user.student.dto.StudentSignupRequest;
import com.ssafy.ourdoc.domain.user.student.service.StudentSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentSignupController {

    private final StudentSignupService studentSignupService;

    // 1. 학생 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody StudentSignupRequest request) {
        // ↓ try-catch 없이, 예외 발생 시 GlobalExceptionHandler로 전달
        Long studentId = studentSignupService.signup(request);
        return ResponseEntity.ok("학생 회원가입 완료. student_id = " + studentId);
    }
}
