package com.ssafy.ourdoc.domain.user.teacher.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentListResponse;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileResponseDto;
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

	private final TeacherService teacherService;

	// 1. 교사 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestPart TeacherSignupRequest request,
		@RequestPart MultipartFile certificateFile) {
		teacherService.signup(request, certificateFile);
		return ResponseEntity.ok("교사 회원가입 완료.");
	}

	// 2. QR 생성
	@GetMapping(value = "/{teacherId}/code", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateTeacherInviteCode(@PathVariable Long teacherId) {
		byte[] qrImage = teacherService.generateTeacherClassQr(teacherId);
		return ResponseEntity.ok(qrImage);
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
	public StudentListResponse getStudentList(@Login User user, Pageable pageable) {
		return teacherService.getMyClassStudentList(user, pageable);
	}

	// 5. 소속 변경 승인 대기 학생 목록 조회
	@GetMapping("/students/pending")
	public Page<StudentPendingProfileDto> getPendingStudentList(@Login User user, Pageable pageable) {
		return teacherService.getPendingStudentList(user, pageable);
	}

	// 6. 교사 본인 정보 조회
	@GetMapping("/profile")
	public TeacherProfileResponseDto getTeacherProfile(@Login User user) {
		return teacherService.getTeacherProfile(user);
	}

	@PatchMapping(value = "/profile")
	public ResponseEntity<String> updateTeacherProfile(
		@Login User user,
		@RequestPart(required = false) MultipartFile profileImage,
		@RequestPart TeacherProfileUpdateRequest request
	) {
		teacherService.updateTeacherProfile(user, profileImage, request);
		return ResponseEntity.ok("교사 정보가 수정되었습니다.");
	}
}
