package com.ssafy.ourdoc.domain.user.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.user.student.dto.StudentSignupRequest;
import com.ssafy.ourdoc.domain.user.student.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	// 1. 학생 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody StudentSignupRequest request) {
		// ↓ try-catch 없이, 예외 발생 시 GlobalExceptionHandler로 전달
		Long studentId = studentService.signup(request);
		return ResponseEntity.ok("학생 회원가입 완료. student_id = " + studentId);
	}
}
