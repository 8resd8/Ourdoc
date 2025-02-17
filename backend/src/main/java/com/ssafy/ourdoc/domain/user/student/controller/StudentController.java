package com.ssafy.ourdoc.domain.user.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.dto.StudentAffiliationChangeRequest;
import com.ssafy.ourdoc.domain.user.student.dto.StudentSignupRequest;
import com.ssafy.ourdoc.domain.user.student.service.StudentService;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	// 1. 학생 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody StudentSignupRequest request) {
		studentService.signup(request);
		return ResponseEntity.ok("학생 회원가입 완료");
	}

	// 2. 학생 소속 변경 요청(학년 변경시)
	@PostMapping("/request")
	public ResponseEntity<String> studentAffiliationChange(@Login User user,
		@RequestBody StudentAffiliationChangeRequest request) {
		studentService.requestStudentAffiliationChange(user, request);
		return ResponseEntity.ok("학생 소속 변경 신청 완료");
	}

	// 3. 학생 본인 정보 조회
	@GetMapping("/profile")
	public ResponseEntity<?> getStudentProfile(@Login User user) {
		return studentService.getStudentProfile(user);
	}

	// 4. 학생 프로필 사진 수정
	@PatchMapping("/profile")
	public ResponseEntity<String> updateProfileImage(@Login User user, @RequestPart MultipartFile profileImage) {
		studentService.updateProfileImage(user, profileImage);
		return ResponseEntity.ok("학생 프로필 이미지가 수정되었습니다.");
	}

}
