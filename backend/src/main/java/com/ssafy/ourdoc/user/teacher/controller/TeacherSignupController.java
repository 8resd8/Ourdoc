package com.ssafy.ourdoc.user.teacher.controller;

import com.ssafy.ourdoc.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.user.teacher.service.TeacherSignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherSignupController {

    private final TeacherSignupService teacherSignupService;

    // 1. 교사 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody TeacherSignupRequest request) {
        log.info("교사 회원가입 진입");
        Long teacherId = teacherSignupService.signup(request);
        return ResponseEntity.ok("교사 회원가입 완료. teacher_id = " + teacherId);
    }
}
