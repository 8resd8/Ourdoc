package com.ssafy.ourdoc.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.user.dto.CheckIdRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LogoutResponse;
import com.ssafy.ourdoc.domain.user.dto.request.CheckPasswordRequest;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.service.UserService;
import com.ssafy.ourdoc.global.annotation.Login;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtUtil jwtUtil;

	// 1. 사용자 로그인
	@PostMapping("/signin")
	public ResponseEntity<?> Login(@RequestBody LoginRequest request) {
		return userService.login(request);
	}

	// 2. ID 중복 체크
	@PostMapping("/checkId")
	public ResponseEntity<Boolean> checkDuplicateLoginId(@RequestBody CheckIdRequest request) {
		boolean isDuplicate = userService.isLoginIdDuplicate(request);
		return ResponseEntity.ok(isDuplicate);
	}

	// 3. 로그아웃
	@PostMapping("/signout")
	public ResponseEntity<LogoutResponse> logout(HttpServletRequest request, HttpServletResponse response) {
		String token = jwtUtil.resolveToken(request);
		LogoutResponse logoutResponse = userService.logout(token);

		if ("401".equals(logoutResponse.resultCode())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(logoutResponse);
		}

		// ✅ Refresh Token 삭제 (쿠키에서도 삭제)
		response.addHeader("Set-Cookie", "Refresh-Token=; HttpOnly; Secure; Path=/; Max-Age=0");

		return ResponseEntity.ok(logoutResponse);
	}

	// 4. 비밀번호 일치 확인
	@PostMapping("/password/verification")
	public ResponseEntity<Boolean> verifyPassword(@Login User user, @RequestBody CheckPasswordRequest request) {
		boolean isDuplicate = userService.verifyPassword(user, request);
		return ResponseEntity.ok(isDuplicate);
	}
}
