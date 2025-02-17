package com.ssafy.ourdoc.domain.user.teacher.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.ssafy.ourdoc.domain.user.teacher.dto.QrResponseDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentListResponse;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileUpdateRequest;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.dto.VerificateAffiliationChangeRequest;
import com.ssafy.ourdoc.domain.user.teacher.service.TeacherService;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

	@Value("${prod.QrUrl}")
	private String prodQrUrl;

	@Value("${prod.ChangeQrUrl}")
	private String prodChangeQrUrl;

	private final TeacherService teacherService;

	// 1. 교사 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestPart TeacherSignupRequest request,
		@RequestPart MultipartFile certificateFile) {
		teacherService.signup(request, certificateFile);
		return ResponseEntity.ok("교사 회원가입 완료.");
	}

	// 2. QR 생성(회원가입)
	@GetMapping(value = "/code")
	public ResponseEntity<QrResponseDto> generateSignupCode(@Login User user) {
		QrResponseDto response = teacherService.generateTeacherClassQr(user, prodQrUrl);
		return ResponseEntity.ok(response);
	}

	// QR 생성(단순 소속 변경)
	@GetMapping("/change/code")
	public ResponseEntity<QrResponseDto> generateChangeCode(@Login User user) {
		QrResponseDto response = teacherService.generateTeacherClassQr(user, prodChangeQrUrl);
		return ResponseEntity.ok(response);
	}

	// 3. 학생 소속 변경 승인/거부
	@PatchMapping("/verification")
	public ResponseEntity<String> verificateAffiliationChange(@Login User user,
		@RequestBody VerificateAffiliationChangeRequest request) {
		String response = teacherService.verificateAffiliationChange(user, request);
		return ResponseEntity.ok(response);
	}

	// 4. 본인 학급 학생 목록 조회
	@GetMapping("/students/profile")
	public StudentListResponse getStudentList(@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return teacherService.getMyClassStudentList(user, pageable);
	}

	@GetMapping("/students/pending")
	public Page<StudentPendingProfileDto> getPendingStudentList(@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return teacherService.getPendingStudentList(user, pageable);
	}

	// 6. 교사 본인 정보 조회
	@GetMapping("/profile")
	public ResponseEntity<?> getTeacherProfile(@Login User user) {
		return teacherService.getTeacherProfile(user);
	}

	// 교사 정보 수정
	@PatchMapping("/profile")
	public ResponseEntity<String> updateTeacherProfile(
		@Login User user,
		@RequestPart(required = false) MultipartFile profileImage,
		@RequestPart TeacherProfileUpdateRequest request
	) {
		teacherService.updateTeacherProfile(user, profileImage, request);
		return ResponseEntity.ok("교사 정보가 수정되었습니다.");
	}

	// 교사 학급 생성(활성화)
	@PostMapping("/class")
	public ResponseEntity<String> createClass(@Login User user) {
		teacherService.createClass(user);
		return ResponseEntity.ok("학급이 생성되었습니다.");
	}
}
