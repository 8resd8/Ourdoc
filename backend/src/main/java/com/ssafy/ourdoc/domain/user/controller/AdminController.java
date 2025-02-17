package com.ssafy.ourdoc.domain.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.user.dto.TeacherVerificationDto;
import com.ssafy.ourdoc.domain.user.dto.request.TeacherVerificationRequest;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.service.AdminService;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/teachers/verification")
	public Page<TeacherVerificationDto> getPendingTeachers(@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return adminService.getPendingTeachers(user, pageable);
	}

	@PatchMapping("/verification")
	public ResponseEntity<String> verifyTeacher(@Login User user,
		@RequestBody TeacherVerificationRequest request) {
		String response = adminService.verifyTeacher(user, request);
		return ResponseEntity.ok(response);
	}
}
