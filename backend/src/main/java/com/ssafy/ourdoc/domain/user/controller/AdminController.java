package com.ssafy.ourdoc.domain.user.controller;

import com.ssafy.ourdoc.domain.user.dto.request.TeacherVerificationRequest;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.service.AdminService;
import com.ssafy.ourdoc.domain.user.dto.TeacherVerificationDto;
import com.ssafy.ourdoc.global.annotation.Login;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/teachers/verification")
	public Page<TeacherVerificationDto> getPendingTeachers(@Login User user, Pageable pageable) {
		return adminService.getPendingTeachers(user, pageable);
	}

	@PatchMapping("/verification")
	public ResponseEntity<String> verifyTeacher(@Login User user, @RequestBody TeacherVerificationRequest request) {
		adminService.verifyTeacher(user, request);
		return ResponseEntity.ok("교사 인증 요청 처리 완료");
	}
}
