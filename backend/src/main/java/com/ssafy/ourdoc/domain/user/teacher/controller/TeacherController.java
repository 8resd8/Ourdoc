package com.ssafy.ourdoc.domain.user.teacher.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.service.TeacherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

	private final TeacherService teacherService;

	// 1. 교사 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody TeacherSignupRequest request) {
		Long teacherId = teacherService.signup(request);
		return ResponseEntity.ok("교사 회원가입 완료. teacher_id = " + teacherId);
	}

	// 2. QR 생성
	@GetMapping(value = "/{teacherId}/code", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateTeacherInviteCode(@PathVariable Long teacherId) {
		byte[] qrImage = teacherService.generateTeacherClassQr(teacherId);
		return ResponseEntity.ok(qrImage);
	}
}
