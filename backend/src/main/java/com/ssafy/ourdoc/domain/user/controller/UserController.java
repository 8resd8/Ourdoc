package com.ssafy.ourdoc.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse;
import com.ssafy.ourdoc.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * POST /users/signin
	 * Body:
	 * {
	 *   "userType": "학생",
	 *   "loginId": "test1234",
	 *   "password": "test1234!"
	 * }
	 */
	// 1. 사용자 로그인
	@PostMapping("/signin")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse response = userService.login(request);

		// resultCode = "401"이면 Unauthorized(401), 그 외는 200
		if ("401".equals(response.resultCode())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		return ResponseEntity.ok(response);
	}

	// 2. ID 중복 체크
	@GetMapping("/check-id/{loginId}")
	public ResponseEntity<Boolean> checkDuplicateLoginId(@PathVariable String loginId) {
		boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
		return ResponseEntity.ok(isDuplicate);
	}
}
