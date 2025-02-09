package com.ssafy.ourdoc.domain.user.teacher.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentListResponse;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.service.TeacherService;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

	private final TeacherService teacherService;

	// 1. 교사 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestPart TeacherSignupRequest request, @RequestPart MultipartFile certificateFile) {
		teacherService.signup(request, certificateFile);
		return ResponseEntity.ok("교사 회원가입 완료.");
	}

	// 2. QR 생성
	@GetMapping(value = "/{teacherId}/code", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateTeacherInviteCode(@PathVariable Long teacherId) {
		byte[] qrImage = teacherService.generateTeacherClassQr(teacherId);
		return ResponseEntity.ok(qrImage);
	}

	// 본인 학급 학생 목록 조회
	@GetMapping("/students/profile")
	public StudentListResponse getStudentList(@Login User user, Pageable pageable) {
		return teacherService.getMyClassStudentList(user, pageable);
	}
}
