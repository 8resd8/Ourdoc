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
import com.ssafy.ourdoc.domain.user.dto.LogoutResponse;
import com.ssafy.ourdoc.domain.user.service.UserService;
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
	private final JwtRefreshService jwtRefreshService;

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
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
		LoginResponse loginResponse  = userService.login(request);

		// resultCode = "401"이면 Unauthorized(401), 그 외는 200
		if ("401".equals(loginResponse.resultCode())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
		}

		// ✅ UserService에서 Refresh Token을 가져와서 쿠키로 저장
		String refreshToken = jwtRefreshService.getRefreshToken(loginResponse.user().id());

		// ✅ Refresh Token을 `HttpOnly` 쿠키로 설정 (자동 전송)
		response.addHeader("Set-Cookie", "Refresh-Token=" + refreshToken + "; HttpOnly; Secure; Path=/");

		return ResponseEntity.ok(loginResponse);
	}

	// 2. ID 중복 체크
	@GetMapping("/{loginId}")
	public ResponseEntity<Boolean> checkDuplicateLoginId(@PathVariable("loginId") String loginId) {
		boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
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
}
